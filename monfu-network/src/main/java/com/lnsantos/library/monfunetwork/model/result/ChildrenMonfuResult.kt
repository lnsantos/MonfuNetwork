package com.lnsantos.library.monfunetwork.model.result

class MonfuSuccess<T: Any>(val data: T) : MonfuResult<T>
class MonfuFailed<T: Any>(val code: Int, val errorBody: String? = null) : MonfuResult<T>
open class MonfuUnknown<T: Any>(val exception: Throwable): MonfuResult<T>