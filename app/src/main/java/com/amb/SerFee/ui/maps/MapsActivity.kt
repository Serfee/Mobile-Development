package com.amb.SerFee.ui.maps

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.amb.SerFee.R
import com.amb.SerFee.databinding.ActivityMapsBinding
import com.amb.SerFee.util.ViewModelFactory
import com.amb.SerFee.data.Result
import com.amb.SerFee.data.model.Story
import com.amb.SerFee.ui.story.AddStoryActivity
import com.amb.SerFee.ui.story.DetailStoryActivity
import com.amb.SerFee.ui.story.MainActivity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var mapsViewModel: MapsViewModel
    private val boundBuilder = LatLngBounds.Builder()
    private lateinit var mMap: GoogleMap

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getMyLocation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupViewModel()

        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.run {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        setupStory()
        setMapStyle()
        getMyLocation()
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        mapsViewModel = ViewModelProvider(this, factory).get(MapsViewModel::class.java)
    }

    private fun setMapStyle() {
        try {
            val success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
            )
            if (!success) {
                Log.e(ContentValues.TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(ContentValues.TAG, "Can't find style. Error: ", exception)
        }
    }

    private fun setupStory() {
        mapsViewModel.getUser().observe(this) { user ->
            val token = "Bearer ${user.token}"
            mapsViewModel.getStoryLocation(token).observe(this) { result ->
                when (result) {
                    is Result.Success -> showMarker(result.data.listStory)
                    is Result.Error -> Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    else -> {}
                }
            }
        }
    }

    private fun showMarker(data: List<Story>) {
        data.forEach { story ->
            val latLng = LatLng(story.lat, story.lon)
            print(latLng)
            val marker = mMap.addMarker(
                MarkerOptions().position(latLng)
                    .title(getString(R.string.story_from) + story.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                    .alpha(0.7f)
                    .snippet(story.description)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            boundBuilder.include(latLng)
            marker?.tag = story
            mMap.setOnInfoWindowClickListener { marker ->
                val intent = Intent(this, DetailStoryActivity::class.java).apply {
                    putExtra(DetailStoryActivity.EXTRA_DETAIL, marker.tag as Story)
                }
                startActivity(intent)
            }
        }
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val userLatLng = LatLng(location.latitude, location.longitude)
                    val cameraPosition = CameraPosition.Builder()
                        .target(userLatLng)
                        .zoom(10.5f)
                        .tilt(0f)
                        .bearing(0f)
                        .build()

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                startActivity(Intent(this, MainActivity::class.java))
                return true
                // Handle Home menu item click
                // Implement your logic here
                //return true
            }
            R.id.menu_maps -> {
                //startActivity(Intent(this, MapsActivity::class.java))
                return true
            }
            R.id.menu_add_story -> {
                startActivity(Intent(this, AddStoryActivity::class.java))
                // The AddStoryActivity will be shown by the BottomNavigationView
                return true
            }
        }
        return false
    }
}

