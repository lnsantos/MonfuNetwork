package com.lnsantos.library.monfunetwork.call

import okhttp3.Request
import okio.Timeout
import retrofit2.Call

internal abstract class BaseMonfuCall<T, R>(
    private val request: Call<R>
) : Call<T> {
    override fun isExecuted() = request.isExecuted
    override fun cancel() = request.cancel()
    override fun isCanceled() = request.isCanceled
    override fun request(): Request = request.request()
    override fun timeout(): Timeout = request.timeout()
}
