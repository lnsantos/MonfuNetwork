package com.lnsantos.library.monfunetwork.request

import com.lnsantos.library.monfunetwork.model.MonfuFailed
import com.lnsantos.library.monfunetwork.model.MonfuResult
import com.lnsantos.library.monfunetwork.model.MonfuSuccess
import com.lnsantos.library.monfunetwork.model.MonfuUnknown
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MonfuRequestSupportImpl<T: Any>(
    private val callback: Callback<MonfuResult<T>>,
    private val requestCall: Call<MonfuResult<T>>
) : MonfuRequestSupport<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val body = response.body()
        val code = response.code()

        when (response.isSuccessful && body != null) {
            true -> {
                val successResponse = MonfuSuccess<T>(body) as MonfuResult<T>
                val result = Response.success(successResponse)
                callback.onResponse(requestCall, result)
            }
            else -> {
                val failedResponse = MonfuFailed<T>(
                    code = code,
                    errorBody = response.toString()
                ) as MonfuResult<T>
                val result = Response.success(failedResponse)
                callback.onResponse(requestCall, result)
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        val failedResponse = MonfuUnknown<T>(t) as MonfuResult<T>
        val result = Response.success(failedResponse)
        callback.onResponse(requestCall, result)
    }
}