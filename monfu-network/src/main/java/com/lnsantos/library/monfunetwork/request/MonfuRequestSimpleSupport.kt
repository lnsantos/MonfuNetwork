package com.lnsantos.library.monfunetwork.request

import com.lnsantos.library.monfunetwork.model.MonfuResult

internal interface MonfuRequestSimpleSupport<T : Any> {
    suspend fun getResult() : MonfuResult<T>
}