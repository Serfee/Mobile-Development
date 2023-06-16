package com.amb.SerFee.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amb.SerFee.R
import com.amb.SerFee.databinding.ActivityCleaningBinding
import com.amb.SerFee.databinding.ActivityMainBinding
import com.amb.SerFee.databinding.ActivityProfileBinding
import com.amb.SerFee.ui.fragment.CleaningActivity
import com.amb.SerFee.ui.story.MainActivity

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        binding.fabBack.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_back -> {
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
        }
    }
}