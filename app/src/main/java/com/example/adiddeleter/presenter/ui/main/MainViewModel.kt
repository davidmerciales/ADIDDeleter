package com.example.adiddeleter.presenter.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adiddeleter.data.model.request.GetAdidRequest
import com.example.adiddeleter.domain.usecase.DeleteAdidUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteAdidUsecase: DeleteAdidUsecase
) : ViewModel() {
    val state: MainContract.MainState = MainContract.MutableMainState()

    private val _event = MutableSharedFlow<MainContract.MainEvent>()
    val event: SharedFlow<MainContract.MainEvent> = _event

    init {
        viewModelScope.launch {
            event.collectLatest { event ->
                when (event) {
                    is MainContract.MainEvent.OnDeleteADID -> delete(event.adid)
                }
            }
        }
    }

    private fun delete(id: String) {
        state.isLoading = true
        viewModelScope.launch {
            deleteAdidUsecase(
                params = getParams(id),
                onSuccess = {
                    handleSuccess(it)
                },
                onFailure = {
                    handleError(it)
                }
            )
        }
    }

    private fun handleSuccess(response: HttpStatusCode) {
        state.message = response.description
        when (response.value) {
            200 -> {
                state.adid = ""
                state.isSuccess = true
            }
            404 -> {
                state.isFailed = true
            }
            else -> {
                state.isFailed = true
            }
        }
        state.isLoading = false
        Log.d("delete: success", response.value.toString())
    }

    private fun handleError(exception: Exception) {
        Log.d("delete: failed", exception.message ?: "Unknown error")
        state.message = exception.message ?: "Unknown error"
        state.isLoading = false
        state.isFailed = true
    }

    private fun getParams(adid: String): GetAdidRequest {
        val appToken = when (state.selectedValue) {
            Variants.VALUE_9900.string -> "5v3fdxdrw4qo"
            Variants.VALUE_9901.string -> ""
            Variants.VALUE_9906.string -> ""
            else -> ""
        }
        val token = when (state.selectedValue) {
            Variants.VALUE_9900.string -> "o1Z-mbJG24HPATyUnrHB"
            Variants.VALUE_9901.string -> ""
            Variants.VALUE_9906.string -> ""
            else -> ""
        }

        return GetAdidRequest(
            advertisingId = adid.trim(),
            appToken = appToken,
            token = token
        )
    }

    fun setEvent(event: MainContract.MainEvent) {
        viewModelScope.launch { _event.emit(event) }
    }
}
