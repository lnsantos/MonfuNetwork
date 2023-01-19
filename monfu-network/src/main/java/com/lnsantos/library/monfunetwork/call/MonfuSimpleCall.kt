package com.lnsantos.library.monfunetwork.call

import com.lnsantos.library.monfunetwork.model.*
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

internal class MonfuSimpleCall<T : Any>(
    private val request: Call<T>
) : Call<MonfuResult<T>> {

    override fun execute(): Response<MonfuResult<T>> {
        if (isExecuted) {
            return Response.success(MonfuRequestAlreadExecuted<T>() as MonfuResult<T>)
        }

        return runBlocking {
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
    }

    override fun enqueue(callback: Callback<MonfuResult<T>>) = throw NotImplementedError("this never will call")
    override fun clone(): Call<MonfuResult<T>> = MonfuSimpleCall(request.clone())
    override fun isExecuted() = request.isExecuted
    override fun cancel() = request.cancel()
    override fun isCanceled() = request.isCanceled
    override fun request() = request.request()
    override fun timeout() = request.timeout()
}