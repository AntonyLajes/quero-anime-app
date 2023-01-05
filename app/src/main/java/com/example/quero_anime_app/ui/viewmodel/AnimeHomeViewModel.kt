package com.example.quero_anime_app.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quero_anime_app.R
import com.example.quero_anime_app.ui.model.remote.FirebaseClient
import com.example.quero_anime_app.util.models.ResultModel

class AnimeHomeViewModel(application: Application) : AndroidViewModel(application) {

    private val firestoreClient = FirebaseClient.getFirestoreClient()
    private val currentUser = FirebaseClient.getFirebaseClient().currentUser
    private val context = application.applicationContext
    private var _favoriteResult: MutableLiveData<ResultModel> = MutableLiveData()
    val favoriteResult: LiveData<ResultModel> get() = _favoriteResult
    private var animeList: List<String> = ArrayList()
    private var receivedDBAnimeList: MutableList<String> = ArrayList()
    private var _hasFavorited: MutableLiveData<Boolean> = MutableLiveData()
    val hasFavorited: LiveData<Boolean> get() = _hasFavorited

    fun favoriteHandler(animeId: String) {
        if (currentUser?.uid != null) {
            firestoreClient.collection(context.getString(R.string.users_collection))
                .whereArrayContains(context.getString(R.string.anime_id), animeId)
                .get()
                .addOnSuccessListener { anime ->
                    _hasFavorited.value = !anime.isEmpty
                }
                .addOnFailureListener {
                    _hasFavorited.value = false
                }
            firestoreClient.collection(context.getString(R.string.users_collection))
                .document(currentUser?.uid ?: "")
                .get()
                .addOnSuccessListener { document ->
                    val dbAnimeList = document.data!!.values.toList()
                    if (dbAnimeList.isEmpty()) {
                        receivedDBAnimeList.add(animeId)
                        addAnimeId(receivedDBAnimeList)
                    } else {
                        animeList = dbAnimeList[0] as List<String>

                        if (animeList.isNotEmpty()) {
                            for (anime in animeList) {
                                receivedDBAnimeList.add(anime)
                            }
                            receivedDBAnimeList.add(animeId)
                            addAnimeId(receivedDBAnimeList)
                        }
                    }
                }
        } else {
            ResultModel(false, context.getString(R.string.error_favorite_login))
        }
    }

    private fun addAnimeId(generatedAnimeList: MutableList<String>) {
        val animeListMap = hashMapOf(
            context.getString(R.string.anime_id) to generatedAnimeList.toList()
        )
        firestoreClient.collection(context.getString(R.string.users_collection))
            .document(currentUser?.uid ?: "")
            .set(animeListMap)
            .addOnCompleteListener {
                _favoriteResult.value =
                    ResultModel()
            }
            .addOnFailureListener {
                _favoriteResult.value =
                    ResultModel(false, context.getString(R.string.error_favorite))
            }
    }

    fun getAnimeById(animeId: String) {
        firestoreClient.collection(context.getString(R.string.users_collection))
            .whereArrayContains(context.getString(R.string.anime_id), animeId)
            .get()
            .addOnSuccessListener { anime ->
                if (!anime.isEmpty) {
                    _favoriteResult.value =
                        ResultModel()
                }else{
                    _favoriteResult.value =
                        ResultModel(result = false)
                }
            }
            .addOnFailureListener {
                _favoriteResult.value =
                    ResultModel(result = false)
            }
    }

    private fun removeAnimeById(animeId: String){

    }

}