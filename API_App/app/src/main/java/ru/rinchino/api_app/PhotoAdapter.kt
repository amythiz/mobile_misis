package ru.rinchino.api_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import ru.rinchino.api_app.databinding.ItemFoxBinding

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val photos = mutableListOf<String>()

    fun setData(newPhotos: List<String>) {
        photos.clear()
        photos.addAll(newPhotos)
        notifyDataSetChanged()
    }

    class PhotoViewHolder(private val binding: ItemFoxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photoUrl: String) {
            binding.textView.text = "Лиса # ${adapterPosition + 1}"

            Glide.with(binding.imageView.context)
                .load(photoUrl)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemFoxBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ) // инициализируеи биндинг
        return PhotoViewHolder(binding) // передаём биндинг
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position]) // обращаемся по очереди по позициям
    }
}