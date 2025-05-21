package ru.rinchino.api_app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters = _characters.asStateFlow()

    fun getCharacters(page: Int){
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                APIClient.instance.getCharacters(page).execute()
            }

            if (response.isSuccessful) {
                _characters.value = response.body()?.results ?: emptyList()
            } else {
                _characters.value = emptyList()
            }

        }
    }
    fun getFoxes(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _characters.value = emptyList()
                for (i in 1..5) {
                    Log.d("MyTag", "m1111!!!: $i")

                    val response = APIClient.instance2.getFox().execute()

                    Log.d("MyTag", "message!!!: $i")

                    if (response.isSuccessful) {

                        // достаём данные

                        val newCharacter = Character(
                            id = i,
                            name = "Лиса ${i}",
                            status = "-",
                            species = "-",
                            gender = "-",
                            image = response.body()?.image ?: ""
                        )
                        _characters.value += newCharacter
                    }
//                else {
//                    _characters.value = emptyList()
//                }
                }
            }

        }
    }
}