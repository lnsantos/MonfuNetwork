package com.lnsantos.library.monfunetwork

import com.lnsantos.library.monfunetwork.model.MonfuResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MonfuNetworkCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        private const val FIRST_POSITION = 0

        @JvmStatic
        fun create() = MonfuNetworkCallAdapterFactory()
    }

    override fun get(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val mainType = getRawType(type)

        // don't use coroutines
        if (mainType == MonfuResult::class.java) {
            val subtype = getParameterUpperBound(FIRST_POSITION, type as ParameterizedType)
            return MonfuNetworkSimpleCallAdapter<Any>(subtype)
        }

        // use coroutines
        if(mainType != Call::class.java) {
            throw ClassCastException("please use suspend function to continue example suspend fun get(): MonfuResult<String>")
        }
        val internalRaw = getParameterUpperBound(FIRST_POSITION, type as ParameterizedType)
        val internalRawType = getRawType(internalRaw)
        if (internalRawType != MonfuResult::class.java) {
            throw ClassCastException("please use MonfuResult to continue example suspend fun get(): MonfuResult<String>")
        }

        val resultType = getParameterUpperBound(FIRST_POSITION, internalRaw as ParameterizedType)
        return MonfuNetworkCallAdapter(resultType)
    }
}
