package com.example.quero_anime_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quero_anime_app.ui.model.remote.AnimesDbConnections
import com.example.quero_anime_app.util.adapter.AnimeSearchedAdapter
import com.example.quero_anime_app.util.listeners.RetrofitListener
import com.example.quero_anime_app.util.models.AnimeDbModel
import com.example.quero_anime_app.util.models.QueryModel
import com.example.quero_anime_app.util.models.ResultAnimeDbModel

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private var _animeList: MutableLiveData<ResultAnimeDbModel> = MutableLiveData()
    val animeList: LiveData<ResultAnimeDbModel> get() = _animeList
    private val animesDbConnections = AnimesDbConnections()

    fun getAnimeByName(animeName: String){
        animesDbConnections.getAnimes(QueryModel("1", "25", animeName, null, "ranking", null), object: RetrofitListener<ResultAnimeDbModel>{
            override fun onSuccess(response: ResultAnimeDbModel) {
                _animeList.value = response
            }

            override fun onError(error: String) {
                val s = error
            }

        })
    }

}