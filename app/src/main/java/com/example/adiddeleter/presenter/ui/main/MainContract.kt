package com.example.adiddeleter.presenter.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.adiddeleter.data.model.request.GetAdidRequest


class MainContract {
    sealed interface MainEvent {
        data class OnDeleteADID(val adid: String) : MainEvent
    }

    interface MainState {
        var adid: String
        var selectedIndex: Int
        var selectedValue: String
        var param: GetAdidRequest?
        var isLoading: Boolean
        var isSuccess: Boolean
        var isFailed: Boolean
        var message: String
    }

    class MutableMainState : MainState {
        override var adid: String by mutableStateOf("")
        override var selectedIndex: Int by mutableStateOf(0)
        override var param: GetAdidRequest? by mutableStateOf(null)
        override var selectedValue: String by mutableStateOf("9900")
        override var isLoading: Boolean by mutableStateOf(false)
        override var isSuccess: Boolean by mutableStateOf(false)
        override var isFailed: Boolean by mutableStateOf(false)
        override var message: String by mutableStateOf("")
    }
}

enum class Variants(val string: String) {
    VALUE_9900("9900"),
    VALUE_9901("9901"),
    VALUE_9906("9906")
}