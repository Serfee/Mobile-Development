package com.amb.SerFee.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.LinearLayoutManager
import com.amb.SerFee.R
import com.amb.SerFee.data.StoryPagingSource
import com.amb.SerFee.databinding.ActivityCleaningBinding
import com.amb.SerFee.ui.adapter.LoadingStateAdapter
import com.amb.SerFee.ui.adapter.StoryAdapter
import com.amb.SerFee.ui.story.MainActivity
import com.amb.SerFee.ui.story.MainViewModel
import com.amb.SerFee.ui.user.LoginActivity
import com.amb.SerFee.ui.user.UserViewModel
import com.amb.SerFee.util.ViewModelFactory

class CleaningActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCleaningBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCleaningBinding.inflate(layoutInflater)
        setContentView(binding.root )
        supportActionBar?.hide()


        setupViewModel()
        setupView()
        setupOnClickListeners()
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
    }

    private fun setupView() {
        storyAdapter = StoryAdapter()
        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                setStory()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        with(binding.rvStory) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CleaningActivity)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
        }
    }

    private fun setStory() {
        mainViewModel.getStory().observe(this) {
            storyAdapter.submitData(lifecycle, it)
            showProgressIndicator(false)
        }
    }

    //FOR FLOATING ACTION BUTTON
    private fun setupOnClickListeners() {
        binding.fabBack.setOnClickListener(this)
    }

    //FOR FLOATING ACTION BUTTON
    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_back -> {
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
        }
    }

    private fun showProgressIndicator(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.clearStory()
    }
}