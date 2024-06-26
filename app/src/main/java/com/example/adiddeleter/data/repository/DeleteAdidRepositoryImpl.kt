package com.example.adiddeleter.data.repository

import com.example.adiddeleter.data.model.request.DeleteAdidRequest
import com.example.adiddeleter.data.model.response.GetAdidResponse
import com.example.adiddeleter.data.remote.ApiService
import com.example.adiddeleter.domain.repository.DeleteAdidRepository
import com.example.adiddeleter.domain.repository.GetAdidRepository
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class DeleteAdidRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): DeleteAdidRepository {
    override suspend fun deleteAdidResponse(params: DeleteAdidRequest): HttpStatusCode {
        return apiService.deleteADID(params)
    }
}