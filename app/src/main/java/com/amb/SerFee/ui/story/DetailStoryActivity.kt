package com.amb.SerFee.ui.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amb.SerFee.R
import com.amb.SerFee.data.model.Story
import com.amb.SerFee.databinding.ActivityDetailStoryBinding
import com.amb.SerFee.util.createProgress
import com.bumptech.glide.Glide

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private var story: Story? = null

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater )
        setContentView(binding.root)


        story = intent.getParcelableExtra<Story>(EXTRA_DETAIL) as Story

        supportActionBar?.title = getString(R.string.detail_story)

        viewSetup()
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
}