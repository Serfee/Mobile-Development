package com.amb.SerFee.data.model

import com.google.gson.annotations.SerializedName

data class CurrentUser (
    @SerializedName("user_id")
    val id: String,
    @SerializedName("full_name")
    val name: String?,
    @SerializedName("phone_number")
    val number: String?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("image_url")
    val photoUrl: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("latitude")
    val lat: Double,
    @SerializedName("longitude")
    val lon: Double,
    @SerializedName("token")
    val token: String
)