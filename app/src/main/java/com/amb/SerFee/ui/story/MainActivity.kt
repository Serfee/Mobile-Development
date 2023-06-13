package com.amb.SerFee.ui.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.amb.SerFee.R
import com.amb.SerFee.data.StoryPagingSource
import com.amb.SerFee.data.model.Story
import com.amb.SerFee.data.networking.api.ApiService
import com.amb.SerFee.data.preference.UserPreferences
import com.amb.SerFee.databinding.ActivityMainBinding
import com.amb.SerFee.ui.fragment.CleaningActivity
import com.amb.SerFee.ui.fragment.FixingFragment
import com.amb.SerFee.ui.fragment.GardeningFragment
import com.amb.SerFee.ui.maps.MapsActivity
import com.amb.SerFee.ui.user.LoginActivity
import com.amb.SerFee.ui.user.UserViewModel
import com.amb.SerFee.util.ViewModelFactory


import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity (): AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        setupMVModel()
        onClick()
    }

    private fun onClick() {
        binding.fabLogOut.setOnClickListener(::onClickLogOut)
        binding.fabSetting.setOnClickListener(::onClickSetting)
        binding.btnCleaning.setOnClickListener (::onClickCleaning)

    }
    private fun onClickCleaning(view: View) {
        startActivity(Intent(this, CleaningActivity::class.java))
        finishAffinity()
    }


    private fun onClickLogOut(view: View) {
        userViewModel.logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    private fun onClickSetting(view: View) {
        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    }



    private fun setupMVModel() {
        val factory = ViewModelFactory.getInstance(this)

        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                // Handle Home menu item click
                return true
            }
            R.id.menu_maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
                return true
            }
            R.id.menu_add_story -> {
                startActivity(Intent(this, AddStoryActivity::class.java))
                return true
            }
        }
        return false
    }
}