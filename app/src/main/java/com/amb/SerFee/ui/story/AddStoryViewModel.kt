package com.amb.SerFee.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amb.SerFee.data.model.User
import com.amb.SerFee.data.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody? = null,
        lon: RequestBody? = null,
        category_id: RequestBody,

        ) = repository.addStory(token, file, description, lat, lon, category_id)

    fun getUser(): LiveData<User> = repository.getUserData()
}