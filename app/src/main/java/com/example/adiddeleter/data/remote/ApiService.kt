package com.example.adiddeleter.data.remote
import com.example.adiddeleter.data.model.request.DeleteAdidRequest
import com.example.adiddeleter.data.model.request.GetAdidRequest
import com.example.adiddeleter.data.model.response.BaseResponse
import com.example.adiddeleter.data.model.response.GetAdidResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


interface ApiService {

    suspend fun getADID(params: GetAdidRequest): BaseResponse<GetAdidResponse>

    suspend fun deleteADID(params: DeleteAdidRequest): HttpStatusCode
}