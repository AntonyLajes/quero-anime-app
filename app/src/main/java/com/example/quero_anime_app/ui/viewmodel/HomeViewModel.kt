package com.example.quero_anime_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quero_anime_app.ui.model.remote.AnimesDbConnections
import com.example.quero_anime_app.util.listeners.RetrofitListener
import com.example.quero_anime_app.util.models.AnimeDbModel
import com.example.quero_anime_app.util.models.QueryModel
import com.example.quero_anime_app.util.models.ResultAnimeDbModel
import kotlin.random.Random

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val animesDbConnections = AnimesDbConnections()
    private var _resultAnimeModels: MutableLiveData<List<AnimeDbModel>> = MutableLiveData()
    val resultAnimeModels: LiveData<List<AnimeDbModel>> get() = _resultAnimeModels
    private var _resultMostWatchedAnimeModels: MutableLiveData<ResultAnimeDbModel> =
        MutableLiveData()
    val resultMostWatchedAnimeModels: LiveData<ResultAnimeDbModel> get() = _resultMostWatchedAnimeModels
    private var _resultSerieMostWatchedAnimeModels: MutableLiveData<ResultAnimeDbModel> =
        MutableLiveData()
    val resultSerieMostWatchedAnimeModels: LiveData<ResultAnimeDbModel> get() = _resultSerieMostWatchedAnimeModels
    private var _resultMovieMostWatchedAnimeModels: MutableLiveData<ResultAnimeDbModel> =
        MutableLiveData()
    val resultMovieMostWatchedAnimeModels: LiveData<ResultAnimeDbModel> get() = _resultMovieMostWatchedAnimeModels

    fun getAleatoryAnimes() {
        val ids = List(7) { Random.nextInt(1, 10080) }
        val animeList: MutableList<AnimeDbModel> = ArrayList()
        for (id in ids) {
            animesDbConnections.getAnimeById(id, object : RetrofitListener<AnimeDbModel> {
                override fun onSuccess(response: AnimeDbModel) {
                    animeList.add(response)
                    _resultAnimeModels.value = animeList
                }

                override fun onError(error: String) {
                }
            })
        }
    }

    fun getMostWatched() {
        animesDbConnections.getAnimes(
            QueryModel("1", "10", null, null, "ranking", null),
            object : RetrofitListener<ResultAnimeDbModel> {
                override fun onSuccess(response: ResultAnimeDbModel) {
                    _resultMostWatchedAnimeModels.value = response
                }

                override fun onError(error: String) {
                }

            })
    }

    fun getMostSerieWatched() {
        animesDbConnections.getAnimes(
            QueryModel("1", "100", null, null, "ranking", null),
            object : RetrofitListener<ResultAnimeDbModel> {
                override fun onSuccess(response: ResultAnimeDbModel) {
                    _resultSerieMostWatchedAnimeModels.value = response
                }

                override fun onError(error: String) {
                }

            })
    }

    fun getMostMovieWatched() {
        animesDbConnections.getAnimes(
            QueryModel("1", "100", null, null, "ranking", null),
            object : RetrofitListener<ResultAnimeDbModel> {
                override fun onSuccess(response: ResultAnimeDbModel) {
                    _resultMovieMostWatchedAnimeModels.value = response
                }

                override fun onError(error: String) {
                }

            })
    }

}