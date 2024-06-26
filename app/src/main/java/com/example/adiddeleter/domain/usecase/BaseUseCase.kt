package com.example.adiddeleter.domain.usecase

import com.example.adiddeleter.domain.utils.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Exception, Type>

    operator fun invoke(
        params: Params,
        onSuccess: suspend (Type) -> Unit = {},
        onFailure: suspend (Exception) -> Unit = {},
        scope: CoroutineScope = MainScope()
    ) {
        scope.launch(Dispatchers.IO) {
            val result = run(params)
            scope.launch (Dispatchers.Main){
                result.fold(
                    failed = { onFailure(it) },
                    succeeded = {
                        onSuccess(it)
                    }
                )
            }
        }
    }
}