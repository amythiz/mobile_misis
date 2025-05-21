package ru.rinchino.api_app

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private const val URL = "https://rickandmortyapi.com/api/" //free rest api - возвращает json-ответ, а не GraphQL
    private const val URL2 = "https://randomfox.ca"

    val instance: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }
    val instance2: APIService2 by lazy {
        Retrofit.Builder()
            .baseUrl(URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService2::class.java)
    }
}

// Ретрофит - это объект
// Можно добавить логирование

//1. АПИ-клиент обращается к серверу через АПИ-сервис
//2. Пришло