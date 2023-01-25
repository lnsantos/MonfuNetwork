package com.lnsantos.library.monfunetwork.call

import com.lnsantos.library.monfunetwork.exception.MonfuOperationNotSupported
import com.lnsantos.library.monfunetwork.model.result.MonfuResult
import com.lnsantos.library.monfunetwork.request.MonfuRequestSupport
import com.lnsantos.library.monfunetwork.utils.errorUnknownRequestFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MonfuFlowCall<T : Any>(
    private val request: Call<T>
) : BaseMonfuCall<Flow<T>, T>(request){

    override fun clone(): Call<Flow<T>> = MonfuFlowCall(request.clone())
    override fun execute(): Response<Flow<T>> = throw MonfuOperationNotSupported()

    override fun enqueue(callback: Callback<Flow<T>>) {
        try {
            request.enqueue(MonfuRequestSupport.enqueue(callback, this))
        } catch (e: Throwable) {
            callback.onResponse(this, Response.success(e.errorUnknownRequestFlow()))
        }
    }
}