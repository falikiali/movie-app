package com.falikiali.movieapp.presentation.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.falikiali.movieapp.databinding.ActivitySearchBinding
import com.falikiali.movieapp.presentation.main.MovieLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val viewModel: SearchMovieViewModel by viewModels()

    private lateinit var searchPagingAdapter: SearchPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionBar()
        initRv()
        observePagingAdapterLoadState()
        actionBtnRetry()
        searchMovie()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Search Movie"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initRv() {
        val loadStateAdapter = MovieLoadStateAdapter(onClickRetry = {
            searchPagingAdapter.retry()
        })
        searchPagingAdapter = SearchPagingAdapter(onItemClick = {
            Toast.makeText(this@SearchActivity, it.title, Toast.LENGTH_SHORT).show()
        })

        val adapterWithLoadStateAdapter = searchPagingAdapter.withLoadStateFooter(loadStateAdapter)

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = adapterWithLoadStateAdapter
        }
    }

    private fun observeDataPagination() {
        lifecycleScope.launch {
            viewModel.movie.collectLatest {
                binding.rvMovie.scrollToPosition(0)
                searchPagingAdapter.submitData(it)
            }
        }
    }

    private fun observePagingAdapterLoadState() {
        lifecycleScope.launch {
            searchPagingAdapter.loadStateFlow.collectLatest { loadStates: CombinedLoadStates ->
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
            searchPagingAdapter.refresh()
        }
    }

    private fun searchMovie() {
        binding.apply {
            svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    viewModel.searchMovie(p0)
                    observeDataPagination()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
        }
    }
}