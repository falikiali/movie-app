package com.falikiali.movieapp.presentation.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.falikiali.movieapp.R
import com.falikiali.movieapp.databinding.ActivityFavoriteBinding
import com.falikiali.movieapp.presentation.detailmovie.DetailMovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private val binding: ActivityFavoriteBinding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private val viewModel: FavoriteMovieViewModel by viewModels()

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpActionBar()
        initRv()
        getAllFavoriteMovie()
        observeViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUpActionBar() {
        supportActionBar?.apply {
            title = "Favorite Movie"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initRv() {
        favoriteAdapter = FavoriteAdapter(onItemClick = {
            val intent = Intent(this@FavoriteActivity, DetailMovieActivity::class.java)
            intent.putExtra(DetailMovieActivity.ID_MOVIE, it.id)
            startActivity(intent)
        })

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favoriteAdapter
        }
    }

    private fun getAllFavoriteMovie() {
        viewModel.getAllFavoriteMovie()
    }

    private fun observeViewModel() {
        viewModel.resultFavoriteMovie.observe(this@FavoriteActivity) {
            binding.lottieEmpty.isVisible = it.isEmpty()
            binding.rvMovie.isVisible = it.isNotEmpty()

            favoriteAdapter.submitList(it)
        }
    }

}