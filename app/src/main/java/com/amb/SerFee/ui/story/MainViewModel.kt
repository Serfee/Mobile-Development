package com.amb.SerFee.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amb.SerFee.data.model.Story
import com.amb.SerFee.data.model.User
import com.amb.SerFee.data.repository.StoryRepository

class MainViewModel (private val repository: StoryRepository) : ViewModel() {

    fun getStory(): LiveData<PagingData<Story>> {
        return  repository.getStory().cachedIn(viewModelScope)
    }

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }
}