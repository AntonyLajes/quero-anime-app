package com.example.quero_anime_app.util.listeners

interface RetrofitListener<T> {

    fun onSuccess(response: T)
    fun onError(error: String)
}