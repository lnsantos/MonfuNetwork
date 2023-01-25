package com.lnsantos.library.monfunetwork.exception

class MonfuOperationNotSupported(
    message: String = "flow supported only suspend function, please add suspend fun on your service"
) : Throwable(message = message)