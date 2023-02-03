package com.lnsantos.library.monfunetwork

import com.lnsantos.library.monfunetwork.exception.MonfuOperationNotSupported
import com.lnsantos.library.monfunetwork.model.result.MonfuResult
import kotlinx.coroutines.flow.Flow
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
    ): CallAdapter<*, *> {

        val mainType = getRawType(type)
        val subtype = getParameterUpperBound(FIRST_POSITION, type as ParameterizedType)

        // don't use suspend fun (coroutines)
        if (mainType == MonfuResult::class.java) {
            return MonfuNetworkCallAdapter<Any, MonfuResult<Any>>(subtype, MonfuResult::class.java)
        }

        // use suspend fun (coroutines)
        if(mainType != Call::class.java) {
            throw ClassCastException("please use suspend function to continue example suspend fun get(): MonfuResult<String>")
        }

        if (getRawType(subtype) == Flow::class.java) {
            return try {
                val genericType = getParameterUpperBound(FIRST_POSITION, subtype as ParameterizedType)
                MonfuNetworkCallAdapter<Any, Call<Flow<Any>>>(genericType, Flow::class.java)
            } catch (e: Throwable) {
                throw MonfuOperationNotSupported("verify your type generic, must be object to serialized with response body")
            }
        }

        val internalRaw = getParameterUpperBound(FIRST_POSITION, type)
        val internalRawType = getRawType(internalRaw)
        val resultType = getParameterUpperBound(FIRST_POSITION, internalRaw as ParameterizedType)

        return when {
            internalRawType != MonfuResult::class.java -> throw ClassCastException("please use MonfuResult to continue example suspend fun get(): MonfuResult<String>")
            else -> MonfuNetworkCallAdapter<Any, Call<MonfuResult<Any>>>(resultType, Call::class.java)
        }
    }
}
