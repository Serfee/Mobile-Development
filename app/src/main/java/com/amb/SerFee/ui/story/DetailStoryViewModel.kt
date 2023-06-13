package com.amb.SerFee.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amb.SerFee.data.repository.StoryRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amb.SerFee.data.Result
import com.amb.SerFee.data.model.User
import com.amb.SerFee.data.networking.response.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class DetailStoryViewModel(private val repository: StoryRepository) : ViewModel() {
//
//    private val _applyTaskResult = MutableLiveData<Result<BaseResponse>>()
//
//    fun applyJob(token: String, requestId: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                _applyTaskResult.postValue(Result.Loading)
//                val response = repository.applyJob(token, requestId)
//                if (response is Result.Success) {
//                    _applyTaskResult.postValue(response)
//                } else {
//                    _applyTaskResult.postValue(Result.Error("Error occurred while applying job"))
//                }
//            } catch (e: Exception) {
//                val errorMsg = e.message.toString()
//                _applyTaskResult.postValue(Result.Error(errorMsg))
//            }
//        }
//    }
//    fun getUser(): LiveData<User> = repository.getUserData()
//
//}

