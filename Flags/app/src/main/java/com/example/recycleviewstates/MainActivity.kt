package com.example.recycleviewstates

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        val data = listOf(
            State("Бразилия", "Бразилиа", R.drawable.brazil),
            State("Аргентина", "Буэнос-Айрес", R.drawable.argentina),
            State("Перу", "Лима", R.drawable.peru),
            State("Венесуэла", "Каракас", R.drawable.venezuela),
            State("Эквадор", "Кито", R.drawable.ecuador),
            State("Чили", "Сантьяго", R.drawable.chile)
            )

        val adapter = StateAdapter(data)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}