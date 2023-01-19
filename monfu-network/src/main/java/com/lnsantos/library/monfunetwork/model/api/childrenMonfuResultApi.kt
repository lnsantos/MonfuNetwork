package com.lnsantos.library.monfunetwork.model.api

class MonfuSuccess<T: Any>(val data: T) : MonfuResultApi<T, Nothing>
class MonfuFailed<E: Any>(val body: E) : MonfuResultApi<Nothing, E>
open class MonfuUnknown<T: Any>(val exception: Throwable): MonfuResultApi<T, Nothing>