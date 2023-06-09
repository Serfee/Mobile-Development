package com.amb.SerFee.ui.story

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.FusedLocationProviderClient
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.amb.SerFee.R
import com.amb.SerFee.databinding.ActivityAddStoryBinding
import com.amb.SerFee.util.ViewModelFactory
import com.amb.SerFee.util.createCustomTempFile
import com.amb.SerFee.util.reduceFileImage
import com.amb.SerFee.data.Result
import com.amb.SerFee.data.networking.api.ApiService
import com.amb.SerFee.util.uriToFile
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import retrofit2.Response
import com.amb.SerFee.data.networking.response.CategoryResponse
import com.amb.SerFee.data.model.Category
import com.amb.SerFee.ui.maps.MapsActivity
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomnavigation.BottomNavigationView


class AddStoryActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val apiService: ApiService by lazy {
        val client = OkHttpClient.Builder()
            .build()

        Retrofit.Builder()
            .baseUrl("http://192.168.1.155:3000/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }



    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var currentPhotoPath: String
    private lateinit var addStoryViewModel: AddStoryViewModel
    private var location: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var categories: List<String> = emptyList()

    //private val apiService: ApiService = ApiService.create()
    private lateinit var dropdownMenuBinding: MaterialAutoCompleteTextView




    private var getFile: File? = null

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.didnt_get_permission),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        dropdownMenuBinding = binding.dropdownMenu as MaterialAutoCompleteTextView



        setContentView(binding.root)
        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        setupASAVModel()
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        with(binding) {
            btnCamera.setOnClickListener { startTakePhoto() }
            btnGallery.setOnClickListener { openGallery() }
            btnUpload.setOnClickListener { uploadPicture() }
            icSearchLocation.setOnClickListener { getMyCurrentLocation() }

            dropdownMenuBinding.setAdapter<ArrayAdapter<String>>(ArrayAdapter(
                this@AddStoryActivity,
                android.R.layout.simple_dropdown_item_1line,
                categories
            ))
            getCategories()
        }
    }


    private fun setupASAVModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        addStoryViewModel = ViewModelProvider(this, factory)[AddStoryViewModel::class.java]
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyCurrentLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyCurrentLocation()
                }
                else -> {}
            }
        }
    private fun setLocationEditText(location: Location) {
        val latLng = "${location.latitude}, ${location.longitude}"
        this.location = location
        binding.etLocation.setText(latLng)
    }
    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun getMyCurrentLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    setLocationEditText(location)
                } else {
                    Toast.makeText(
                        this@AddStoryActivity,
                        R.string.location_not_found,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun uploadPicture() {
        addStoryViewModel.getUser().observe(this@AddStoryActivity) { user ->
            val token = "Bearer ${user.token}"
            if (getFile != null) {
                val file = reduceFileImage(getFile as File)
                val description = "${binding.etDesc.text}".toRequestBody("text/plain".toMediaType())
                val lat = if (location != null) location?.latitude.toString().toRequestBody("text/plain".toMediaType()) else null
                val lon = if (location != null) location?.longitude.toString().toRequestBody("text/plain".toMediaType()) else null
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestImageFile)
                addStoryViewModel.addStory(token, imageMultipart, description, lat, lon).observe(this@AddStoryActivity) { result ->
                    when (result) {
                        is Result.Success -> {
                            Toast.makeText(this@AddStoryActivity, result.data.message, Toast.LENGTH_SHORT).show()
                            showProgressIndicator(false)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        is Result.Loading -> showProgressIndicator(true)
                        is Result.Error -> {
                            Toast.makeText(this@AddStoryActivity, result.error, Toast.LENGTH_SHORT).show()
                            showProgressIndicator(false)
                        }
                    }
                }
            } else {
                Toast.makeText(this@AddStoryActivity, getString(R.string.input_image_first), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        intentGalleryLauncher.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity,
                "com.amb.SerFee",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            intentCameraLauncher.launch(intent)
        }
    }

    private val intentGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@AddStoryActivity)

            getFile = myFile

            binding.previewImage.setImageURI(selectedImg)
        }
    }

    private val intentCameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            getFile = File(currentPhotoPath)

            BitmapFactory.decodeFile(getFile?.path)?.let { bitmap ->
                binding.previewImage.setImageBitmap(bitmap)
            }
        }
    }
    private fun showProgressIndicator(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun getCategories() {
        addStoryViewModel.getUser().observe(this@AddStoryActivity) { user ->
            val token = "Bearer ${user.token}"
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val categoryResponse = apiService.getCategories(token)
                    val categories = categoryResponse.category.map { it.category_name }
                    if (categories != null) {
                        withContext(Dispatchers.Main) {
                            populateDropdownMenu(categories)
                        }
                    }
                } catch (e: Exception) {
                    // Handle network or other errors
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddStoryActivity,
                            "Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun populateDropdownMenu(categories: List<String>) {
        val adapter = ArrayAdapter(
            this@AddStoryActivity,
            android.R.layout.simple_dropdown_item_1line,
            categories
        )
        dropdownMenuBinding.setAdapter<ArrayAdapter<String>>(adapter)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                startActivity(Intent(this, MainActivity::class.java))
                return true
                // Handle Home menu item click
                // Implement your logic here
                return true
            }
            R.id.menu_maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
                return true
            }
            R.id.menu_add_story -> {
                //startActivity(Intent(this, AddStoryActivity::class.java))
                // The AddStoryActivity will be shown by the BottomNavigationView
                return true
            }
        }
        return false
    }


}