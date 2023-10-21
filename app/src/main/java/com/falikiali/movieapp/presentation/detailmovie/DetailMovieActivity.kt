package com.falikiali.movieapp.presentation.detailmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.falikiali.movieapp.R
import com.falikiali.movieapp.databinding.ActivityDetailMovieBinding
import com.falikiali.movieapp.domain.model.FavoriteMovie
import com.falikiali.movieapp.utils.ResultState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val ID_MOVIE = "id movie"
    }

    private val binding: ActivityDetailMovieBinding by lazy { ActivityDetailMovieBinding.inflate(layoutInflater) }
    private val viewModel: DetailMovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getDetailMovie()
        checkFavoriteMovie()
        observeViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setCollapsingToolbar(title: String) {
        binding.collapsingToolbar.title = title
    }

    private fun setActionBar(title: String) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getDetailMovie() {
        val id = intent.getIntExtra(ID_MOVIE, 0)
        viewModel.getDetailMovie(id)
    }

    private fun checkFavoriteMovie() {
        val id = intent.getIntExtra(ID_MOVIE, 0)
        viewModel.checkFavoriteMovie(id)
    }

    private fun actionBtnFavorite(favoriteMovie: FavoriteMovie) {
        binding.btnFavorite.setOnClickListener {
            viewModel.updateFavoriteMovie(favoriteMovie)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            detailMovieState.observe(this@DetailMovieActivity) {
                with(binding) {
                    progressBar.isVisible = it is ResultState.Loading
                    btnRetry.isVisible = it is ResultState.Failed
                    detailView.isVisible = it is ResultState.Success

                    if (it is ResultState.Success) {
                        setActionBar(it.data.title ?: "Unknown")
                        setCollapsingToolbar(it.data.title ?: "Unknown")

                        tvTitle.text = it.data.title ?: "Unknown"
                        tvReleaseDate.text = it.data.releaseDate
                        tvOverview.text = it.data.overview
                        tvGenres.text = it.data.genres?.joinToString(", ")

                        Glide.with(this@DetailMovieActivity)
                            .load("https://image.tmdb.org/t/p/original" + it.data.posterPath)
                            .into(binding.ivPoster)

                        Glide.with(this@DetailMovieActivity)
                            .load("https://image.tmdb.org/t/p/original" + it.data.backdropPath)
                            .into(binding.ivBackdrop)

                        actionBtnFavorite(
                            FavoriteMovie(
                                it.data.title ?: "Unknown",
                                it.data.posterPath ?: "-",
                                it.data.releaseDate ?: "-",
                                it.data.id ?: 0
                            )
                        )
                    }
                }
            }

            isFavorited.observe(this@DetailMovieActivity) {
                if (it) {
                    binding.btnFavorite.setImageResource(R.drawable.icon_favorite_filled)
                } else {
                    binding.btnFavorite.setImageResource(R.drawable.icon_favorite_outline)
                }
            }

            viewModel.isAddedToFavorite.observe(this@DetailMovieActivity) {
                if (it) {
                    showSnackbar("Add to favorite")
                } else {
                    showSnackbar("Remove to favorite")
                }
            }
        }
    }

    private fun showSnackbar(snackbarMessage: String) {
        Snackbar.make(binding.root, snackbarMessage, Snackbar.LENGTH_SHORT)
            .show()
    }

}