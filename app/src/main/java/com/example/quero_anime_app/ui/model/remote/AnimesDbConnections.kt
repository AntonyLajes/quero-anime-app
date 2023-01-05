package com.example.quero_anime_app.ui.model.remote

import com.example.quero_anime_app.util.constants.Constants
import com.example.quero_anime_app.util.listeners.RetrofitListener
import com.example.quero_anime_app.util.models.AnimeDbModel
import com.example.quero_anime_app.util.models.QueryModel
import com.example.quero_anime_app.util.models.ResultAnimeDbModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class AnimesDbConnections {

    private val retrofitClient = RetrofitClient.getRetrofit(Constants.BaseUrl.ANIMESDB_BASE_URL)
    private val animesDbEndpoint = retrofitClient.create(AnimesDbEndpoint::class.java)
    fun getAnimes(searchData: QueryModel, listener: RetrofitListener<ResultAnimeDbModel>) {
        val getAnimesCallback = animesDbEndpoint.getAnime(
            searchData.page,
            searchData.size,
            searchData.search,
            searchData.genres,
            searchData.sortBy,
            searchData.sortOrder
        )
        getAnimesCallback.enqueue(object : Callback<ResultAnimeDbModel> {
            override fun onResponse(
                call: Call<ResultAnimeDbModel>,
                response: Response<ResultAnimeDbModel>
            ) {
                if (response.code() == Constants.ResponseCode.SUCCESS) {
                    listener.onSuccess(response.body()!!)
                }
            }

            override fun onFailure(call: Call<ResultAnimeDbModel>, t: Throwable) {
                listener.onError(t.message ?: "")
            }
        })
    }

    fun getAnimeById(animeId: Int, listener: RetrofitListener<AnimeDbModel>) {
        val getAnimeByIdCallback = animesDbEndpoint.getAnimeById(animeId)
        getAnimeByIdCallback.enqueue(object : Callback<AnimeDbModel> {
            override fun onResponse(call: Call<AnimeDbModel>, response: Response<AnimeDbModel>) {
                if (response.code() == Constants.ResponseCode.SUCCESS) {
                    listener.onSuccess(response.body()!!)
                }
            }

            override fun onFailure(call: Call<AnimeDbModel>, t: Throwable) {
                listener.onError(t.message ?: "Error")
            }


        })
    }

}