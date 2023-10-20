package com.falikiali.movieapp.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.falikiali.movieapp.databinding.LoadStateLayoutBinding

class MovieLoadStateAdapter(
    val onClickRetry: () -> Unit
): LoadStateAdapter<MovieLoadStateAdapter.MovieLoadStateViewHolder>() {
    override fun onBindViewHolder(
        holder: MovieLoadStateAdapter.MovieLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateAdapter.MovieLoadStateViewHolder {
        val binding = LoadStateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieLoadStateViewHolder(binding)
    }

    inner class MovieLoadStateViewHolder(private val binding: LoadStateLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRetry.setOnClickListener { onClickRetry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState !is LoadState.Loading
        }
    }

}