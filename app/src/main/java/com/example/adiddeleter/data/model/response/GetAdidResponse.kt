package com.example.adiddeleter.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class GetAdidResponse(
    @SerialName("Adid")
    val adid: String
)

@Serializable
data class LastEventTimes(
    @SerialName("aaalxs-View")
    val aaalxsView: String,
    @SerialName("v0ieu8-Register")
    val v0ieu8Register: String
)
