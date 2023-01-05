package com.example.quero_anime_app.util.constants

import com.example.quero_anime_app.util.models.HeaderModel

class Constants private constructor() {
    object AnimeDbHeaders {
        val RAPID_API_KEY = HeaderModel("X-RapidAPI-Key", "7ea476117fmsh8f9bc9c1d47b127p109bdcjsne35f515fc219")
        val RAPID_API_HOST = HeaderModel("X-RapidAPI-Host", "anime-db.p.rapidapi.com")
    }
    object BaseUrl{
        const val ANIMESDB_BASE_URL = "https://anime-db.p.rapidapi.com/"
    }
    object ResponseCode{
        const val SUCCESS = 200
    }
    object ConstantsWords{
        const val ANIME_NAME = "animeName"
    }
}