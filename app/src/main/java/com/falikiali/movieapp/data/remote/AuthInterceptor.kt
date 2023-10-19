package com.falikiali.movieapp.data.remote

import com.falikiali.movieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer ${BuildConfig.TOKEN}")
        return chain.proceed(request.build())
    }
}