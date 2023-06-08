package com.amb.SerFee.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amb.SerFee.R
import com.amb.SerFee.data.model.CurrentUser
import com.amb.SerFee.data.networking.api.ApiService
import com.amb.SerFee.data.networking.response.CurrentResponse
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProfileViewModel : ViewModel() {

    private val apiService: ApiService by lazy {
        val client = OkHttpClient.Builder().build()

        Retrofit.Builder()
            .baseUrl("http://192.168.1.155:3000/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private val _currentUser = MutableLiveData<CurrentUser>()
    val currentUser: LiveData<CurrentUser> = _currentUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getCurrentUser(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentResponse: CurrentResponse = apiService.current(token)
                val currentUser: CurrentUser? = currentResponse.user
                withContext(Dispatchers.Main) {
                    if (currentUser != null) {
                        _currentUser.value = currentUser
                    } else {
                        _error.value = "Failed to retrieve user data"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message
                }
            }
        }
    }
}