package com.lnsantos.library.monfunetwork.call

import com.lnsantos.library.monfunetwork.model.api.MonfuResultApi
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MonfuApiCall<T : Any, E>(
    private val request: Call<T>
) : Call<MonfuResultApi<T, E>> {

    override fun clone(): Call<MonfuResultApi<T, E>> = MonfuApiCall(request.clone())

    override fun execute(): Response<MonfuResultApi<T, E>> {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: Callback<MonfuResultApi<T, E>>) {
        TODO("Not yet implemented")
    }

    override fun isExecuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}
