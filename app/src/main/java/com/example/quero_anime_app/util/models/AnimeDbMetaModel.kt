package com.example.quero_anime_app.util.models

import com.google.gson.annotations.SerializedName

data class AnimeDbMetaModel(
    @SerializedName("page")
    var page: Int,

    @SerializedName("size")
    var size: Int,

    @SerializedName("totalData")
    var totalData: Int,

    @SerializedName("totalPage")
    var totalPage: Int
    )
