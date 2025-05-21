package com.example.guess

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var randomNumber = generateRandomNumber()
    lateinit var guessEditText: EditText
    lateinit var Text: TextView
    lateinit var checkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guessEditText = findViewById(R.id.guessEditText)
        Text = findViewById(R.id.Text)
        checkButton = findViewById(R.id.checkButton)

        checkButton.setOnClickListener {
            checkGuess()
        }
    }

    private fun generateRandomNumber(): Int {
        return Random.nextInt(1, 21)
    }

    private fun checkGuess() {
        val guessString = guessEditText.text.toString()

        // Проверка на пустой ввод
        if (guessString.isEmpty()) {
            Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show()
            return
        }

        // Преобразование в число с обработкой ошибок
        val guessNumber = try {
            guessString.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Некорректное число", Toast.LENGTH_SHORT).show()
            return
        }
        when {
            guessNumber < randomNumber -> {
                Text.text = "Загаданное число больше"
            }
            guessNumber > randomNumber -> {
                Text.text = "Загаданное число меньше"
            }
            else -> {
                Text.text = "Поздравляем! Вы угадали!"
                randomNumber = generateRandomNumber() // Новая игра
                guessEditText.text.clear() // Очистка поля ввода
            }
        }
    }
}