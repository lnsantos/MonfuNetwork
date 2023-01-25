package com.lnsantos.library.monfunetwork.call

import com.lnsantos.library.monfunetwork.model.result.MonfuFailed
import com.lnsantos.library.monfunetwork.model.result.MonfuResult
import com.lnsantos.library.monfunetwork.model.result.MonfuSuccess
import com.lnsantos.library.monfunetwork.model.result.MonfuUnknown
import com.lnsantos.library.monfunetwork.request.MonfuRequestSupport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import retrofit2.*

internal class MonfuCall<T: Any>(
    private val request: Call<T>
) : BaseMonfuCall<MonfuResult<T>, T>(request) {

    companion object {
        private const val GENERIC_ERROR = ""
    }

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
            val request = async(context = Dispatchers.IO){ request.awaitResponse() }
            val response = request.await()
            val body = response.body()
            val code = response.hashCode()

            when (response.isSuccessful && body != null) {
                true -> Response.success(MonfuSuccess(body))
                else -> Response.success(MonfuFailed(code, response.errorBody()?.string() ?: GENERIC_ERROR))
            }
        } catch (e: Throwable) {
            Response.success(MonfuUnknown(e))
        }
    }
    override fun clone(): Call<MonfuResult<T>> = MonfuCall(request.clone())
}
