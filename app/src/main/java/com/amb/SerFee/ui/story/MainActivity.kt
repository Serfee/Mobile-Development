package com.amb.SerFee.ui.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.amb.SerFee.R
import com.amb.SerFee.databinding.ActivityMainBinding
import com.amb.SerFee.ui.adapter.LoadingStateAdapter
import com.amb.SerFee.ui.adapter.StoryAdapter
import com.amb.SerFee.ui.maps.MapsActivity
import com.amb.SerFee.ui.user.LoginActivity
import com.amb.SerFee.ui.user.UserViewModel
import com.amb.SerFee.util.ViewModelFactory


import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
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
        setupView()
        onClick()
    }

    // OnClick{
    private fun onClick() {
        binding.fabLogOut.setOnClickListener(::onClickLogOut)
        //binding.fabAddStory.setOnClickListener(::onClickAddStory)
        binding.fabSetting.setOnClickListener(::onClickSetting)
        //binding.fabMaps.setOnClickListener(::onClickMaps)
    }

    private fun onClickLogOut(view: View) {
        userViewModel.logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    private fun onClickAddStory(view: View) {
        startActivity(Intent(this, AddStoryActivity::class.java))
    }

    private fun onClickSetting(view: View) {
        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    }

    private fun onClickMaps(view: View) {
        startActivity(Intent(this, MapsActivity::class.java))
    }
    // OnClick}

    private fun setupView() {
        storyAdapter = StoryAdapter()
        mainViewModel.getUser().observe(this@MainActivity) { user ->
            if (user.isLogin) {
                setStory()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        with(binding.rvStory) {
            setHasFixedSize(true)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                })
        }
    }

    private fun setStory() {
        mainViewModel.getStory().observe(this@MainActivity) {
            storyAdapter.submitData(lifecycle, it)
            showProgressIndicator(false)
        }
    }

    private fun setupMVModel() {
        val factory = ViewModelFactory.getInstance(this)

        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
    }

    private fun showProgressIndicator(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                // Handle Home menu item click
                // Implement your logic here
                return true
            }
            R.id.menu_maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
                return true
            }
            R.id.menu_add_story -> {
                // The AddStoryActivity will be shown by the BottomNavigationView
                return true
            }
        }
        return false
    }


}
