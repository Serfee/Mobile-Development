package com.amb.SerFee.ui.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.amb.SerFee.R
import com.amb.SerFee.data.Result
import com.amb.SerFee.data.model.ApplyJobRequest
import com.amb.SerFee.data.model.Story
import com.amb.SerFee.data.networking.api.ApiService
import com.amb.SerFee.databinding.ActivityDetailStoryBinding
import com.amb.SerFee.util.ViewModelFactory
import com.amb.SerFee.util.createProgress
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



//class DetailStoryActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityDetailStoryBinding
//    private var story: Story? = null
//
//    private val apiService: ApiService by lazy {
//        val client = OkHttpClient.Builder()
//            .build()
//
//        Retrofit.Builder()
//            .baseUrl("https://serfee-project.as.r.appspot.com")
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)
//    }
//
//    companion object{
//        const val EXTRA_DETAIL = "extra_detail"
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDetailStoryBinding.inflate(layoutInflater )
//        setContentView(binding.root)
//        supportActionBar?.hide()
//
//
//        story = intent.getParcelableExtra<Story>(EXTRA_DETAIL) as Story
//
//
//        supportActionBar?.title = getString(R.string.detail_story)
//
//        viewSetup()
//        setupApplyButton()
//    }
//
//    private fun viewSetup() {
////          val detail = intent.getParcelableExtra<Story>(EXTRA_DETAIL)
//
//        binding.apply {
//            val tanggal = story?.createdAt?.split("T")?.get(0)
//            tvNameDetail.text = story?.name
//            tvCreatedAt.text = getString(R.string.created_at, tanggal)
//            tvDesc.text = story?.description
//        }
//        Glide.with(this)
//            .load(story?.photoUrl)
//            .placeholder(this@DetailStoryActivity.createProgress())
//            .into(binding.imgStory)
//    }
//
//    private fun setupApplyButton() {
//        binding.fabKontol.setOnClickListener {
//            val request = ApplyJobRequest("i can buy u", story?.id ?: -1)
//            val detailStoryViewModel = ViewModelProvider(this).get(DetailStoryViewModel::class.java)
//            detailStoryViewModel.getUser().observe(this@DetailStoryActivity) { user ->
//                val token = "Bearer ${user.token}"
//                detailStoryViewModel.applyTaskResult.observe(this@DetailStoryActivity) { result ->
//                    when (result) {
//                        is Result.Loading -> {
//                            // Show loading progress
//                        }
//                        is Result.Success -> {
//                            val response = result.data
//                            // Handle the success response
//                        }
//                        is Result.Error -> {
//                            val error = result.error
//                            // Handle the error response
//                        }
//                    }
//                }
//                detailStoryViewModel.applyJob(token, request.request_id)
//            }
//        }
//    }
//
//
//}



class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private var story: Story? = null

    private val apiService: ApiService by lazy {
        val client = OkHttpClient.Builder()
            .build()

        Retrofit.Builder()
            .baseUrl("http://192.168.1.113:8000")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private val viewModelFactory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(applicationContext)
    }

    private val detailStoryViewModel: DetailStoryViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        story = intent.getParcelableExtra<Story>(EXTRA_DETAIL) as Story

        supportActionBar?.title = getString(R.string.detail_story)

        viewSetup()
        setupApplyButton()
    }

    private fun viewSetup() {
        binding.apply {
            val tanggal = story?.createdAt?.split("T")?.get(0)
            tvNameDetail.text = story?.name
            tvCreatedAt.text = getString(R.string.created_at, tanggal)
            tvDesc.text = story?.description
        }
        Glide.with(this)
            .load(story?.photoUrl)
            .placeholder(this@DetailStoryActivity.createProgress())
            .into(binding.imgStory)
    }

    private fun setupApplyButton() {
        binding.fabApply.setOnClickListener {
            val request = ApplyJobRequest("i can buy u", story?.id ?: -1)

            detailStoryViewModel.getUser().observe(this@DetailStoryActivity) { user ->
                val token = "Bearer ${user.token}"

                detailStoryViewModel.applyTaskResult.observe(this@DetailStoryActivity) { result ->
                    when (result) {
                        is Result.Loading -> {
                            // Show loading progress
                        }
                        is Result.Success -> {
                            val response = result.data
                            // Handle the success response
                        }
                        is Result.Error -> {
                            val error = result.error
                            // Handle the error response
                        }
                    }
                }

                detailStoryViewModel.applyJob(token, request.request_id)
            }
        }
    }



}