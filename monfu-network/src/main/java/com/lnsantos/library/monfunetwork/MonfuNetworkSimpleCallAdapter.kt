package com.lnsantos.library.monfunetwork

import com.lnsantos.library.monfunetwork.call.MonfuSimpleCall
import com.lnsantos.library.monfunetwork.model.MonfuResult
import com.lnsantos.library.monfunetwork.request.MonfuRequestSupport
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type
internal class MonfuNetworkSimpleCallAdapter<T : Any>(
    private val raw: Type
) : CallAdapter<T, MonfuResult<T>> {

    override fun responseType() = raw
    override fun adapt(call: Call<T>): MonfuResult<T> = requireNotNull(MonfuSimpleCall(call).execute().body())
}