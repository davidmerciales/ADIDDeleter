package com.example.adiddeleter.domain.repository

import com.example.adiddeleter.data.model.request.DeleteAdidRequest
import com.example.adiddeleter.data.model.response.GetAdidResponse
import io.ktor.http.HttpStatusCode

interface DeleteAdidRepository {

    suspend fun deleteAdidResponse(params: DeleteAdidRequest): HttpStatusCode
}