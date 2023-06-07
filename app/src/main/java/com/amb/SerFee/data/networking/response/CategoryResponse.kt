package com.amb.SerFee.data.networking.response

import com.amb.SerFee.data.model.Category
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    val message: String,
    @SerializedName("category")
    val category: List<Category>
)
