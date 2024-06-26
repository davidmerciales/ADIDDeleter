package com.example.adiddeleter.data.model.request

data class DeleteAdidRequest(
    var adid: String,
    val appToken: String,
    val token: String,)
