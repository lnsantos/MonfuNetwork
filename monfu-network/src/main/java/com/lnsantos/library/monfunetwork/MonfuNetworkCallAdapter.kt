package com.lnsantos.library.monfunetwork

import com.lnsantos.library.monfunetwork.call.MonfuCall
import com.lnsantos.library.monfunetwork.call.MonfuFlowCall
import com.lnsantos.library.monfunetwork.model.result.MonfuResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class MonfuNetworkCallAdapter<I : Any, O>(
    private val raw: Type,
    private val type: Class<*>
) : CallAdapter<I, O> {
    override fun responseType() = raw

    override fun adapt(call: Call<I>): O = when(type.simpleName) {
        Call::class.java.simpleName -> MonfuCall(call) as O
        MonfuResult::class.java.simpleName -> requireNotNull(MonfuCall(call).execute().body()) as O
        Flow::class.simpleName -> MonfuFlowCall(call) as O
        else -> throw IllegalArgumentException("type ${type.simpleName} not supported")
    }
}