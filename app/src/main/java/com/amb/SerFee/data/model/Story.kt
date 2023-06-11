package com.amb.SerFee.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story(
    @SerializedName("request_id")
    val id: String,
    @SerializedName("full_name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image_url")
    val photoUrl: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("location_latitude")
    val lat: Double,
    @SerializedName("location_longitude")
    val lon: Double

): Parcelable
