package com.lnsantos.library.monfunetwork.call

import com.lnsantos.library.monfunetwork.model.MonfuResult
import com.lnsantos.library.monfunetwork.model.MonfuUnknown
import com.lnsantos.library.monfunetwork.request.MonfuRequestSupport
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MonfuCall<T: Any>(
    private val response: Call<T>
) : Call<MonfuResult<T>> {

    override fun enqueue(
        callback: Callback<MonfuResult<T>>
    ) = try {
        response.enqueue(MonfuRequestSupport.enqueue(callback, this))
    } catch (e: Throwable) {
        val failedResponse = MonfuUnknown<T>(e) as MonfuResult<T>
        val result = Response.success(failedResponse)
        callback.onResponse(this, result)
    }

    override fun execute(): Response<MonfuResult<T>> = throw NotImplementedError("this never will call")
    override fun clone(): Call<MonfuResult<T>> = MonfuCall(response.clone())
    override fun isExecuted() = response.isExecuted
    override fun cancel() = response.cancel()
    override fun isCanceled() = response.isCanceled
    override fun request() = response.request()
    override fun timeout() = response.timeout()
}