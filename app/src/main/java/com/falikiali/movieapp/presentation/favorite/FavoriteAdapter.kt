package com.falikiali.movieapp.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.falikiali.movieapp.databinding.MovieItemListBinding
import com.falikiali.movieapp.domain.model.FavoriteMovie

class FavoriteAdapter(
    val onItemClick: (FavoriteMovie) -> Unit
): ListAdapter<FavoriteMovie, FavoriteAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteMovie>() {
            override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding = MovieItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: MovieItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavoriteMovie) {
            itemView.setOnClickListener { onItemClick(data) }
            val date = data.releaseDate?.split("-")

            binding.tvTitle.text = data.title
            binding.tvOverview.text = data.overview
            binding.tvReleaseDate.text = date?.get(0) ?: "-"

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/original" + data.posterPath)
                .into(binding.ivPoster)
        }
    }

}