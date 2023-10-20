package com.falikiali.movieapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.falikiali.movieapp.R
import com.falikiali.movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var mainPagingAdapter: MainPagingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionBar()
        initRv()
        observeDataPagination()
        observePagingAdapterLoadState()
        actionBtnRetry()
    }

    private fun setActionBar() {
        supportActionBar?.title = "Movie App"
    }

    private fun initRv() {
        val loadStateAdapter = MovieLoadStateAdapter(onClickRetry = {
            mainPagingAdapter.retry()
        })
        mainPagingAdapter = MainPagingAdapter(onItemClick = {
            Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()
        })

        val adapterWithLoadStateAdapter = mainPagingAdapter.withLoadStateFooter(loadStateAdapter)

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterWithLoadStateAdapter
        }
    }

    private fun observeDataPagination() {
        lifecycleScope.launch {
            viewModel.movies.collectLatest {
                binding.rvMovie.scrollToPosition(0)
                mainPagingAdapter.submitData(it)
            }
        }
    }

    private fun observePagingAdapterLoadState() {
        lifecycleScope.launch {
            mainPagingAdapter.loadStateFlow.collectLatest { loadStates: CombinedLoadStates ->
                with(binding) {
                    progressBar.isVisible = loadStates.refresh is LoadState.Loading
                    rvMovie.isVisible = loadStates.refresh is LoadState.NotLoading
                    btnRetry.isVisible = loadStates.refresh is LoadState.Error
                }
            }
        }
    }

    private fun actionBtnRetry() {
        binding.btnRetry.setOnClickListener {
            mainPagingAdapter.refresh()
        }
    }
}