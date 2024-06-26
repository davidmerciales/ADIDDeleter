package com.example.adiddeleter.domain.usecase

import android.util.Log
import com.example.adiddeleter.data.model.request.DeleteAdidRequest
import com.example.adiddeleter.data.model.request.GetAdidRequest
import com.example.adiddeleter.domain.repository.DeleteAdidRepository
import com.example.adiddeleter.domain.repository.GetAdidRepository
import com.example.adiddeleter.domain.utils.Either
import com.example.adiddeleter.domain.utils.Failure
import com.example.adiddeleter.domain.utils.Success
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAdidUsecase @Inject constructor(
    private val getAdidRepository: GetAdidRepository,
    private val deleteAdidRepository: DeleteAdidRepository
) : UseCase<HttpStatusCode, GetAdidRequest>() {

    override suspend fun run(params: GetAdidRequest): Either<Exception, HttpStatusCode> {
        return try {
            val response = withContext(Dispatchers.IO) {
                val data = getAdidRepository.getAdidResponse(params)

                if (data.data?.adid.isNullOrEmpty()) {
                    throw Exception(data.description ?: "ID not exist!")
                } else {
                    deleteAdidRepository.deleteAdidResponse(
                        DeleteAdidRequest(
                            adid = data.data?.adid?:"",
                            appToken = params.appToken,
                            token = params.token
                        )
                    )
                }
            }
            Success(response)
        } catch (e: Exception) {
            Failure(e)
        }
    }

}