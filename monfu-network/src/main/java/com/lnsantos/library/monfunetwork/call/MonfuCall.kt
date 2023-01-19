package com.lnsantos.library.monfunetwork.call

import com.lnsantos.library.monfunetwork.model.result.MonfuFailed
import com.lnsantos.library.monfunetwork.model.result.MonfuResult
import com.lnsantos.library.monfunetwork.model.result.MonfuSuccess
import com.lnsantos.library.monfunetwork.model.result.MonfuUnknown
import com.lnsantos.library.monfunetwork.request.MonfuRequestSupport
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

internal class MonfuCall<T: Any>(
    private val request: Call<T>
) : Call<MonfuResult<T>> {

    override fun enqueue(
        callback: Callback<MonfuResult<T>>
    ) = try {
        request.enqueue(MonfuRequestSupport.enqueue(callback, this))
    } catch (e: Throwable) {
        val failedResponse = MonfuUnknown<T>(e) as MonfuResult<T>
        val result = Response.success(failedResponse)
        callback.onResponse(this, result)
    }

    override fun execute(): Response<MonfuResult<T>> = runBlocking {
        try {
            val response = request.awaitResponse()
            val body = response.body()
            val code = response.hashCode()

            when (response.isSuccessful && body != null) {
                true -> Response.success(MonfuSuccess(body))
                else -> Response.success(MonfuFailed(code, response.toString()))
            }
        } catch (e: Throwable) {
            Response.success(MonfuUnknown(e))
        }
    }
    override fun clone(): Call<MonfuResult<T>> = MonfuCall(request.clone())
    override fun isExecuted() = request.isExecuted
    override fun cancel() = request.cancel()
    override fun isCanceled() = request.isCanceled
    override fun request() = request.request()
    override fun timeout() = request.timeout()
}