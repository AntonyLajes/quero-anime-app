package com.example.quero_anime_app.util.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class AnimeDbModel(
    @SerializedName("_id")
    val animeId: String,

    @SerializedName("title")
    val animeTitle: String,

    @SerializedName("ranking")
    val animeRanking: Int,

    @SerializedName("alternativeTitles")
    val animeAlternativeTitles: List<String>,

    @SerializedName("genres")
    val animeGenres: List<String>,

    @SerializedName("episodes")
    val animeEpisodes: Int,

    @SerializedName("hasEpisode")
    val hasEpisode: Boolean,

    @SerializedName("hasRanking")
    val hasRanking: Boolean,

    @SerializedName("image")
    val animeImage: String,

    @SerializedName("link")
    val animeLink: String,

    @SerializedName("status")
    val animeStatus: String,

    @SerializedName("synopsis")
    val animeSynopsis: String,

    @SerializedName("thumb")
    val animeThumb: String,

    @SerializedName("type")
    val animeType: String
    ): Parcelable
