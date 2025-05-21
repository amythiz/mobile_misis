package ru.rinchino.api_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.rinchino.api_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = PhotoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false)
//LinearLayoutManager(th
        binding.recyclerView.adapter = adapter

        // подписываемся на флоу с фото
        lifecycleScope.launch {
            viewModel.photos.collectLatest { photos ->
                // устанавливаем фото из флоу в адаптер
                adapter.setData(photos)
            }
        }
        viewModel.loadPhotos() // загрузка фотографий
    }
}