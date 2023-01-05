package com.example.quero_anime_app.util.models

import retrofit2.http.Query

data class QueryModel(
    var page: String = "1",
    var size: String = "10",
    var search: String?,
    var genres: List<String>?,
    var sortBy: String?,
    var sortOrder: String?
)
