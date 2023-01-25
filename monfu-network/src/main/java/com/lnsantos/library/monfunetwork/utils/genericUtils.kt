package com.lnsantos.library.monfunetwork.utils

import retrofit2.Response

internal fun <T : Any> T?.isNotNull() = this != null
internal fun <T: Any> Response<T>.requestIsSuccess() = isSuccessful && body().isNotNull()