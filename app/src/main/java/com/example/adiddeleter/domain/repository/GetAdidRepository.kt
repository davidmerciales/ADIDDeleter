package com.example.adiddeleter.domain.repository

import com.example.adiddeleter.data.model.request.GetAdidRequest
import com.example.adiddeleter.data.model.response.BaseResponse
import com.example.adiddeleter.data.model.response.GetAdidResponse

interface GetAdidRepository {

    suspend fun getAdidResponse(params: GetAdidRequest): BaseResponse<GetAdidResponse>
}