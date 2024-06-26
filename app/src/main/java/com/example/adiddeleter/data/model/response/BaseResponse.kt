package com.example.adiddeleter.data.model.response

data class BaseResponse<D>(
    val status: Int?,
    val description: String?,
    val data: D?
)
