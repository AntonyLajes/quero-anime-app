package com.example.quero_anime_app.ui.model.remote

import com.example.quero_anime_app.util.models.AnimeDbModel
import com.example.quero_anime_app.util.models.ResultAnimeDbModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimesDbEndpoint {
    @GET("anime")
    fun getAnime(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("search") search: String?,
        @Query("genres") genres: List<String>?,
        @Query("sortBy") sortBy: String?,
        @Query("sortOrder") sortOrder: String?,
    ): Call<ResultAnimeDbModel>

    @GET("anime/by-id/{animeId}")
    fun getAnimeById(@Path("animeId") animeId: Int): Call<AnimeDbModel>
}