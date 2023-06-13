package com.amb.SerFee.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amb.SerFee.data.model.Story
import com.amb.SerFee.data.model.User
import com.amb.SerFee.data.repository.StoryRepository

//class CleaningViewModel (private val repository: StoryRepository) : ViewModel() {
//
//    fun getCleaningStories(): LiveData<PagingData<Story>> {
//        return repository.getStory().cachedIn(viewModelScope)
//    }
//
//    fun getUser(): LiveData<User> {
//        return repository.getUserData()
//    }
//}

class CleaningViewModel(private val repository: StoryRepository) : ViewModel() {

    private var cleaningStories: LiveData<PagingData<Story>>? = null

    fun getCleaningStories(): LiveData<PagingData<Story>> {
        val currentStories = cleaningStories
        if (currentStories != null) {
            return currentStories
        }

        val newStories = repository.getStory().cachedIn(viewModelScope)
        cleaningStories = newStories
        return newStories
    }

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }

    fun clearStory() {
        cleaningStories = null
    }
}