package com.falikiali.movieapp.presentation.detailmovie

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.falikiali.movieapp.R
import com.falikiali.movieapp.databinding.ActivityDetailMovieBinding
import com.falikiali.movieapp.domain.model.FavoriteMovie
import com.falikiali.movieapp.utils.Constants
import com.falikiali.movieapp.utils.ResultState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

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
        observeViewModel()
        actionBtnRetry()
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

    private fun actionBtnRetry() {
        binding.btnRetry.setOnClickListener {
            getDetailMovie()
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            detailMovieState.observe(this@DetailMovieActivity) {
                with(binding) {
                    progressBar.isVisible = it is ResultState.Loading
                    btnRetry.isVisible = it is ResultState.Failed
                    detailView.isVisible = it is ResultState.Success
                    appbar.isVisible = it is ResultState.Success

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

                        checkFavoriteMovie()
                        actionBtnFavorite(
                            FavoriteMovie(
                                it.data.title ?: "Unknown",
                                it.data.posterPath ?: "-",
                                it.data.releaseDate ?: "-",
                                it.data.overview ?: "-",
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
                if (it.isAddedToFavorite) {
                    showNotification("Add ${it.movie} to favorite")
                } else {
                    showNotification("Remove ${it.movie} from favorite")
                }
            }
        }
    }

    private fun showNotification(body: String) {
        val builder = NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Movie App")
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = System.currentTimeMillis().toInt() + Constants.NOTIFICATION_ID.toInt()
        mNotificationManager.notify(id, builder.build())
    }

}