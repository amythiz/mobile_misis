package ru.rinchino.api_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    // создаём флоу с фото
    private val _photos = MutableStateFlow<List<String>>(emptyList())
    val photos = _photos.asStateFlow()

    private var isLouding = false
    fun loadPhotos() {
        if (isLouding) return
        isLouding = true

        // загружаем в фоне, отправляем фото в флоу
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

               val newPhotos =
                mutableListOf<String>() // создаём список новых фотографий, которые придут


            repeat(20) { // цикл: загружаем 20 фотографий по 1 штуке
                val response = APIClient.instance.getFox().execute() // ответ сервера (response)

                if (response.isSuccessful) {

                    // достаём данные
                    response.body()?.let { newPhotos.add(it.image) } // it - ответ сервера
                }
            }

            // добавляем фото в флоу
            _photos.value += newPhotos}
        }


    }
}