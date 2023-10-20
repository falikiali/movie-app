package com.falikiali.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @field:SerializedName("success")
    val success: String,

    @field:SerializedName("status_code")
    val statusCode: Int,

    @field:SerializedName("status_message")
    val statusMessage: String
)
