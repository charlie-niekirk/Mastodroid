package me.cniekirk.mastodroid.core.network.util

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class HostSelectionInterceptor : Interceptor {

    private var host: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!host.isNullOrEmpty()) {
            host!!.toHttpUrlOrNull()?.let {
                request = request.newBuilder()
                    .url(it)
                    .build()
            }
        }

        return chain.proceed(request)
    }

    fun setHost(host: String) {
        this.host = host
    }
}