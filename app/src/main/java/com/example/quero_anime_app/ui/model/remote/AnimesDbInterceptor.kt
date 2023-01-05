package com.example.quero_anime_app.ui.model.remote

import com.example.quero_anime_app.util.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AnimesDbInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(Constants.AnimeDbHeaders.RAPID_API_KEY.name, Constants.AnimeDbHeaders.RAPID_API_KEY.value)
            .addHeader(Constants.AnimeDbHeaders.RAPID_API_HOST.name, Constants.AnimeDbHeaders.RAPID_API_HOST.value)
            .build()
        return chain.proceed(request)
    }
}