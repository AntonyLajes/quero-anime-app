package com.example.quero_anime_app.util.models

import com.google.gson.annotations.SerializedName

data class ResultAnimeDbModel(
    @SerializedName("data")
    var data: List<AnimeDbModel>,

    @SerializedName("meta")
    var meta: AnimeDbMetaModel
)
