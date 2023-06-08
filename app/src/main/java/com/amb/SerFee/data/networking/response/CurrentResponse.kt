package com.amb.SerFee.data.networking.response

import com.amb.SerFee.data.model.CurrentUser
import com.google.gson.annotations.SerializedName

data class CurrentResponse(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("loginResult")
    val user: CurrentUser?,
)
