package com.amb.SerFee.ui.profile

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.amb.SerFee.R
import com.amb.SerFee.data.networking.api.ApiService
import com.amb.SerFee.databinding.ActivityProfileBinding
import com.amb.SerFee.ui.maps.MapsViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {

    private val profileViewModel: ProfileViewModel by viewModels()

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

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrentUser()
    }

    private fun getCurrentUser() {
        // Assuming you have an instance of addStoryViewModel available
        profileViewModel.currentUser.observe(this) { user ->
            val token = "Bearer ${user.token}"
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val currentResponse = apiService.current(token)
                    val currentUser = currentResponse.user
                    withContext(Dispatchers.Main) {
                        // Update the UI with the user data
                        binding.profileName.text = currentUser?.name
                        binding.profileEmail.text = currentUser?.email
                        binding.profileBio.text = "Some bio text" // Set an appropriate bio here
                        // Load the profile image using a library like Glide
                        Glide.with(this@ProfileActivity)
                            .load(currentUser?.photoUrl)
                            .placeholder(R.drawable.ic_home)
                            .error(R.drawable.ic_home)
                            .into(binding.profileImage)
                        binding.ratingBar.rating = (currentUser?.rating ?: 0f) as Float
                    }
                } catch (e: Exception) {
                    // Handle network or other errors
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ProfileActivity,
                            "Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
