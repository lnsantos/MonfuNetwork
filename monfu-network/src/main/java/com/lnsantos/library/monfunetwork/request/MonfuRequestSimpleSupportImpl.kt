package com.lnsantos.library.monfunetwork.request

import com.lnsantos.library.monfunetwork.model.MonfuFailed
import com.lnsantos.library.monfunetwork.model.MonfuSuccess
import com.lnsantos.library.monfunetwork.model.MonfuUnknown
import retrofit2.Call
import retrofit2.awaitResponse

internal class MonfuRequestSimpleSupportImpl<T : Any>(
    private val requestCall: Call<T>
) : MonfuRequestSimpleSupport<T> {

    override suspend fun getResult() = try {
        val response = requestCall.awaitResponse()
        val body = response.body()
        val code = response.hashCode()

        when (response.isSuccessful && body != null) {
            true -> MonfuSuccess(body)
            else -> MonfuFailed(code, response.toString())
        }
    } catch (e: Throwable) {
        MonfuUnknown(e)
    }
}