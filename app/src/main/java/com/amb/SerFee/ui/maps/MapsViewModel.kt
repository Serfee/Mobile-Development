package com.amb.SerFee.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amb.SerFee.data.model.User
import com.amb.SerFee.data.repository.StoryRepository

class MapsViewModel (private val repository: StoryRepository): ViewModel() {

    fun getStoryLocation(token: String) =
        repository.getStoryLocation(token)

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }
}