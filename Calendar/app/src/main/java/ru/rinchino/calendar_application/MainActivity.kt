package ru.rinchino.calendar_application

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var eventsMap: MutableMap<String, MutableList<String>> = mutableMapOf()
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locale = Locale("ru", "RU")
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        val calendarView: CalendarView = findViewById(R.id.calendarView)
        val btnAddEvent: ImageView = findViewById(R.id.btnAddEvent)
        val eventList: ListView = findViewById(R.id.eventList)
        val noEventsText: TextView = findViewById(R.id.noEventsText)
        val selectedDateText: TextView = findViewById(R.id.selectedDateText)


        loadEventsFromFile()

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.time)
            selectedDateText.text = "$selectedDate:"
            showEvents(eventList, noEventsText)
        }

        btnAddEvent.setOnClickListener {
            showAddEventDialog(eventList, noEventsText)
        }

        selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        selectedDateText.text = "$selectedDate:"
        showEvents(eventList, noEventsText)
    }

    override fun onStop() {
        super.onStop()
        saveEventsToFile()
    }

    private fun showEvents(eventList: ListView, noEventsText: TextView) {
        val events = eventsMap[selectedDate] ?: mutableListOf()
        if (events.isEmpty()) {
            noEventsText.visibility = View.VISIBLE
            eventList.visibility = View.GONE
        } else {
            noEventsText.visibility = View.GONE
            eventList.visibility = View.VISIBLE
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, events)
            eventList.adapter = adapter

            eventList.setOnItemClickListener { _, _, position, _ ->
                showEditOrDeleteDialog(events[position], eventList, noEventsText)
            }
        }
    }

    private fun showAddEventDialog(eventList: ListView, noEventsText: TextView) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_event, null)
        val eventNameInput = dialogView.findViewById<EditText>(R.id.eventNameInput)

        AlertDialog.Builder(this)
            .setTitle("Добавить мероприятие")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val eventName = eventNameInput.text.toString()
                if (eventName.isNotEmpty()) {
                    val events = eventsMap.getOrPut(selectedDate) { mutableListOf() }
                    events.add(eventName)
                    showEvents(eventList, noEventsText)
                } else {
                    Toast.makeText(
                        this,
                        "Название мероприятия не может быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .create()
            .show()
    }


    private fun showEditOrDeleteDialog(event: String, eventList: ListView, noEventsText: TextView) {
        AlertDialog.Builder(this)
            .setTitle("Выберите действие")
            .setItems(arrayOf("Редактировать", "Удалить")) { _, which ->
                when (which) {
                    0 -> showEditEventDialog(event, eventList, noEventsText)
                    1 -> deleteEvent(event, eventList, noEventsText)
                }
            }
            .create()
            .show()
    }

    private fun showEditEventDialog(event: String, eventList: ListView, noEventsText: TextView) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_event, null)
        val eventNameInput = dialogView.findViewById<EditText>(R.id.eventNameInput)
        eventNameInput.setText(event)

        AlertDialog.Builder(this)
            .setTitle("Редактировать мероприятие")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val newEventName = eventNameInput.text.toString()
                if (newEventName.isNotEmpty()) {
                    eventsMap[selectedDate]?.let { events ->
                        events.remove(event)
                        events.add(newEventName)
                    }
                    showEvents(eventList, noEventsText)
                } else {
                    Toast.makeText(this, "Название мероприятия не может быть пустым", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .create()
            .show()
    }

    private fun deleteEvent(event: String, eventList: ListView, noEventsText: TextView) {
        eventsMap[selectedDate]?.let { events ->
            events.remove(event)
            showEvents(eventList, noEventsText)
        }
    }

    private fun saveEventsToFile() {
        try {
            openFileOutput("events.json", MODE_PRIVATE).use { fos ->
                val json = Gson().toJson(eventsMap)
                fos.write(json.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Ошибка сохранения данных", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadEventsFromFile() {
        try {
            openFileInput("events.json").use { fis ->
                val isr = InputStreamReader(fis)
                eventsMap = Gson().fromJson(isr, object : TypeToken<MutableMap<String, MutableList<String>>>() {}.type)
                    ?: mutableMapOf()
                isr.close()
            }
        } catch (e: FileNotFoundException) {
            eventsMap = mutableMapOf()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
        }
    }
}

