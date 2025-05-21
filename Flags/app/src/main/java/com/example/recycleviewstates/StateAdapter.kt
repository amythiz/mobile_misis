package com.example.recycleviewstates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleviewstates.databinding.StateItemBinding

//    Адаптер, который используется в RecyclerView,
//    должен наследоваться от абстрактного класса RecyclerView.Adapter.

class StateAdapter(private var items: List<State>): RecyclerView.Adapter<StateAdapter.ViewHolder>(){

    class ViewHolder (val binding: StateItemBinding): RecyclerView.ViewHolder(binding.root)

// Этот класс определяет три метода:
//    1) onCreateViewHolder: возвращает объект ViewHolder, который будет хранить данные по одному объекту
//    2) getItemCount: возвращает количество объектов в списке
//    3) onBindViewHolder: выполняет привязку объекта ViewHolder к объекту State по определенной позиции.

//    Для хранения данных в классе адаптера определен статический класс ViewHolder,
//    который использует определенные в list_item.xml элементы управления

    // установка разметки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = StateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //  возвращает количество объектов в списке RecyclerView
    override fun getItemCount(): Int = items.size

    // выполняет привязку объекта ViewHolder к объекту State по определенной позиции
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val state = items[position] // один из элементов

        with(holder.binding){
            nameView.text = "Страна: ${state.name}"
            capitalView.text = "Столица: ${state.capital}"
            flagView.setImageResource(state.flag)
            //holder.flagView.setImageResource(state.getFlagResource())

        }
    }

    // для обновления данных
    fun updateItems(newItems: List<State>){
        items = newItems
        notifyDataSetChanged()
    }


}