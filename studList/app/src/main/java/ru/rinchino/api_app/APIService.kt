package ru.rinchino.api_app

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("character") // надо использьзовать это команду, чтобы получить персонажа
    fun getCharacters(@Query("page") page: Int): Call<CharacterResponse>
}
interface APIService2 {

    @GET("floof/")
    fun getFox(): Call<FoxResponse>
}


