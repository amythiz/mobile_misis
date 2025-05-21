package ru.rinchino.api_app

import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("floof/")
    fun getFox(): Call<FoxResponse>
}


