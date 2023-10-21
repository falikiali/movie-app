package com.falikiali.movieapp.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.falikiali.movieapp.databinding.MovieItemListBinding
import com.falikiali.movieapp.domain.model.Movie

class SearchPagingAdapter(
    val onItemClick: (Movie) -> Unit
): PagingDataAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ListViewHolder(private val binding: MovieItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            itemView.setOnClickListener { onItemClick(data) }
            val date = data.releaseDate?.split("-")

            with(binding) {
                tvTitle.text = data.title ?: "Unknown"
                tvOverview.text = data.overview ?: "-"
                tvReleaseDate.text = date?.get(0) ?: "-"

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original" + data.posterPath)
                    .into(binding.ivPoster)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            when (holder) {
                is ListViewHolder -> holder.bind(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MovieItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

}