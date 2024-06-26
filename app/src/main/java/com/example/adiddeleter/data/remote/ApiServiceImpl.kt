package com.example.adiddeleter.data.remote

import com.example.adiddeleter.data.model.request.DeleteAdidRequest
import com.example.adiddeleter.data.model.request.GetAdidRequest
import com.example.adiddeleter.data.model.response.BaseResponse
import com.example.adiddeleter.data.model.response.GetAdidResponse
import com.example.adiddeleter.data.utils.Constants.ADID
import com.example.adiddeleter.data.utils.Constants.ADJUST_SERVER_URL
import com.example.adiddeleter.data.utils.Constants.ADVERTISING_ID
import com.example.adiddeleter.data.utils.Constants.APP_TOKEN
import com.example.adiddeleter.data.utils.Constants.AUTHORIZATION
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): ApiService {
    override suspend fun getADID(params: GetAdidRequest): BaseResponse<GetAdidResponse> {
        val response = httpClient.get("${ADJUST_SERVER_URL}/inspect_device"){
            parameter(ADVERTISING_ID, params.advertisingId)
            parameter(APP_TOKEN, params.appToken)
            headers {
                append(AUTHORIZATION, "Bearer ${params.token}")
            }
        }

        return if (response.status.value == 200) {
            val responseBody = response.body<GetAdidResponse>()
            BaseResponse(
                status = response.status.value,
                description = "Success",
                data = responseBody
            )
        } else {
            val errorMessage = response.bodyAsText()
            BaseResponse(
                status = response.status.value,
                description = errorMessage,
                data = null
            )
        }
    }

    override suspend fun deleteADID(params: DeleteAdidRequest): HttpStatusCode {
        val response = httpClient.post("${ADJUST_SERVER_URL}/forget_device"){
            parameter(ADID, params.adid)
            parameter(APP_TOKEN, params.appToken)
            headers {
                append(AUTHORIZATION, "Bearer ${params.token}")
            }
        }
        return response.status
    }
}