package com.amb.SerFee.data.networking.api

import com.amb.SerFee.data.networking.response.BaseResponse
import com.amb.SerFee.data.networking.response.CategoryResponse
import com.amb.SerFee.data.networking.response.CurrentResponse
import com.amb.SerFee.data.networking.response.LoginResponse
import com.amb.SerFee.data.networking.response.StoryResponse
import com.amb.SerFee.data.request.LoginRequest
import com.amb.SerFee.data.request.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): BaseResponse


    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("users/current")
    suspend fun current(
        @Header("Authorization") token: String,
    ): CurrentResponse

    @GET("tasks")
    suspend fun getStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): StoryResponse

    @GET("tasks")
    suspend fun getStoryLocation(
        @Header("Authorization") token: String,
        @Query("location") location : Int = 1,
    ) : StoryResponse

    @GET("tasks/category")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): CategoryResponse

    @Multipart
    @POST("tasks/request")
    suspend fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") lon: RequestBody? = null,
    ): BaseResponse
}