package com.lnsantos.library.monfunetwork.request

import com.lnsantos.library.monfunetwork.utils.errorUnknownRequestFlow
import com.lnsantos.library.monfunetwork.utils.successfulRequestFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MonfuRequestFlowSupportImpl<T : Any>(
    private val callback: Callback<Flow<T>>,
    private val requestCall: Call<Flow<T>>
) : MonfuRequestSupport<T> {

    override fun onResponse(
        call: Call<T>,
        response: Response<T>
    ) {
        callback.onResponse(
            requestCall,
            Response.success(response.successfulRequestFlow())
        )
    }

    override fun onFailure(
        call: Call<T>,
        t: Throwable
    ) {
        val result = Response.success(t.errorUnknownRequestFlow<T>())
        callback.onResponse(requestCall, result)
    }
}
