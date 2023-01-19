package com.lnsantos.library.monfunetwork

import com.lnsantos.library.monfunetwork.call.MonfuCall
import com.lnsantos.library.monfunetwork.model.result.MonfuResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class MonfuNetworkCallAdapter<I : Any, O>(
    private val raw: Type,
    private val isAsync: Boolean
) : CallAdapter<I, O> {
    override fun responseType() = raw

    override fun adapt(call: Call<I>): O = when(isAsync) {
        true -> MonfuCall(call) as O
        false -> requireNotNull(MonfuCall(call).execute().body()) as O
    }
}