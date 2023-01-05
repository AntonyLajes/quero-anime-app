package com.example.quero_anime_app.ui.model.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private lateinit var INSTANCE: Retrofit
        private val client = OkHttpClient.Builder().apply {
            addInterceptor(AnimesDbInterceptor())
        }.build()

        fun getRetrofit(path: String): Retrofit {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(path)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE
        }
    }

}