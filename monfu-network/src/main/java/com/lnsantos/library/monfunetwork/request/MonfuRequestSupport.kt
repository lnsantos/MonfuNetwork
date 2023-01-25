package com.lnsantos.library.monfunetwork.request

import com.lnsantos.library.monfunetwork.model.result.MonfuResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback

internal interface MonfuRequestSupport<T: Any> : Callback<T> {
    companion object {
        @JvmStatic
        fun <T : Any> enqueue(
            callback: Callback<MonfuResult<T>>,
            requestCall: Call<MonfuResult<T>>
        ) : MonfuRequestSupport<T> = MonfuRequestSupportImpl(callback, requestCall)

        @JvmStatic
        @JvmName("flowEnqueue")
        fun <T: Any> enqueue(
            callback: Callback<Flow<T>>,
            requestCall: Call<Flow<T>>
        ) : MonfuRequestSupport<T> = MonfuRequestFlowSupportImpl(callback, requestCall)
     }
}

