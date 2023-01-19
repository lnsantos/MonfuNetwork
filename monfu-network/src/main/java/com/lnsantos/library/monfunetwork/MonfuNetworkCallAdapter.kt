package com.lnsantos.library.monfunetwork

import com.lnsantos.library.monfunetwork.call.MonfuCall
import com.lnsantos.library.monfunetwork.model.MonfuResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class MonfuNetworkCallAdapter(
    private val raw : Type
) : CallAdapter<Type, Call<MonfuResult<Type>>> {
    override fun responseType() = raw
    override fun adapt(call: Call<Type>) = MonfuCall(call)
}