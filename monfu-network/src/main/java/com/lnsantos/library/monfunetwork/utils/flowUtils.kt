package com.lnsantos.library.monfunetwork.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Response
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
internal fun <T: Any> Response<T>.successfulRequestFlow() : Flow<T> = flow {
    emit(
        suspendCancellableCoroutine { coroutine ->
            try {
                if (this@successfulRequestFlow.requestIsSuccess()) {
                    coroutine.resume(body()!!) { coroutine.resumeWithException(it) }
                }
            } catch (e: Throwable) {
                coroutine.resumeWithException(e)
            }
        }
    )
}

internal fun <T : Any> Throwable.errorUnknownRequestFlow() : Flow<T> = flow {
    emit(suspendCancellableCoroutine { it.resumeWithException(this@errorUnknownRequestFlow)})
}