package com.amb.SerFee.ui.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.amb.SerFee.R
import com.amb.SerFee.data.model.ApplyJobRequest
import com.amb.SerFee.data.model.Story
import com.amb.SerFee.data.networking.api.ApiService
import com.amb.SerFee.databinding.ActivityDetailStoryBinding
import com.amb.SerFee.util.createProgress
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private var story: Story? = null

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

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater )
        setContentView(binding.root)
        supportActionBar?.hide()


        story = intent.getParcelableExtra<Story>(EXTRA_DETAIL) as Story

        supportActionBar?.title = getString(R.string.detail_story)

        viewSetup()
        //setupApplyButton()
    }

    private fun viewSetup() {
//          val detail = intent.getParcelableExtra<Story>(EXTRA_DETAIL)

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


//    private fun setupApplyButton() {
//        binding.fabKontol.setOnClickListener {
//            val request = ApplyJobRequest("i can buy u", story?.requestId ?: -1)
//            val detailStoryViewModel = ViewModelProvider(this).get(DetailStoryViewModel::class.java)
//
//            detailStoryViewModel.getUser().observe(this@DetailStoryActivity) { user ->
//                val token = "Bearer ${user.token}" // Retrieve the user token from the ViewModel
//
//                GlobalScope.launch(Dispatchers.Main) {
//                    try {
//                        val response = apiService.applyJob(token, request.request_id)
//                        // Handle the response as needed
//                        if (response.error == false) {
//                            // API call was successful
//                            // Do something with the response
//                        } else {
//                            // API call failed
//                            // Handle the error
//                        }
//                    } catch (e: Exception) {
//                        // Handle the exception
//                    }
//                }
//            }
//        }
//    }

}