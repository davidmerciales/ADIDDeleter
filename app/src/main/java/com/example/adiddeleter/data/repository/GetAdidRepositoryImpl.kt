package com.example.adiddeleter.data.repository

import com.example.adiddeleter.data.model.request.GetAdidRequest
import com.example.adiddeleter.data.model.response.BaseResponse
import com.example.adiddeleter.data.model.response.GetAdidResponse
import com.example.adiddeleter.data.remote.ApiService
import com.example.adiddeleter.domain.repository.GetAdidRepository
import javax.inject.Inject

class GetAdidRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): GetAdidRepository {

    override suspend fun getAdidResponse(params: GetAdidRequest): BaseResponse<GetAdidResponse> {
        return apiService.getADID(params)
    }
}