package com.impressico.moviesapp.presentation.popularmovies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.impressico.moviesapp.data.remote.model.PopularMovieItem
import com.impressico.moviesapp.data.remote.model.PopularTVResult
import com.impressico.moviesapp.data.remote.model.PopularTVShow
import com.impressico.moviesapp.domain.model.PopularListDto
import com.impressico.moviesapp.presentation.adapters.PopularMovieListAdapter
import com.impressico.moviesapp.presentation.states.UIState
import com.impressico.moviesapp.presentation.util.customSnackBar
import com.impressico.moviesapp.presentation.viewmodels.PopularMovieViewModel
import com.impressico.moviesapp.presentation.viewmodels.PopularTVShowViewModel
import com.impressico.recipesapp.databinding.FragmentPopularMoviesListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesList : Fragment() {


    private lateinit var mBinding: FragmentPopularMoviesListBinding
    private val viewModel: PopularMovieViewModel by viewModels()

    private val tvShowViewModel: PopularTVShowViewModel by viewModels()

    private val TAG = "PopularMoviesList"
    private lateinit var mAdapter: PopularMovieListAdapter

    private lateinit var tvShowListAdapter: PopularMovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPopularMoviesListBinding.inflate(inflater, container, false)
        mAdapter = PopularMovieListAdapter { movieId ->
            val action = PopularMoviesListDirections.actionToMovieDetailItem(movieId,true)
            findNavController().navigate(action)
        }
        tvShowListAdapter = PopularMovieListAdapter { tvShowId ->
            Log.d(TAG, "onCreateView: TV Show id $tvShowId")
            val action = PopularMoviesListDirections.actionToMovieDetailItem(tvShowId,false)
            findNavController().navigate(action)
        }
        mBinding.popularMoviesList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                context,
                1,
                RecyclerView.HORIZONTAL,
                false
            )
            adapter = mAdapter
        }
        mBinding.latestTvShowList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                context,
                1,
                RecyclerView.HORIZONTAL,
                false
            )
            adapter = tvShowListAdapter
        }


        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPopularMovies()
        tvShowViewModel.getTVShowList()
        collectMoviesList()
        collectTVShowsList()
    }

    private fun collectMoviesList() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularMovieList.collect {
                    viewModel.popularMovieList.collect { resultUIState ->
                        when (resultUIState) {
                            is UIState.Error -> {
                                mBinding.progressCircular.customProgressbar.visibility = View.GONE
                                customSnackBar(mBinding.root, "${resultUIState.errorMsg}", true)
                                Log.e(TAG, "onCreate: error")
                            }
                            is UIState.Exception -> {
                                customSnackBar(mBinding.root, "${resultUIState.message}", true)
                                Log.e(TAG, "onCreate: Exception")
                            }
                            UIState.Ideal -> {}
                            UIState.Loading -> {
                                mBinding.progressCircular.customProgressbar.visibility =
                                    View.VISIBLE
                            }
                            UIState.NoInternet -> {
                                customSnackBar(
                                    mBinding.root,
                                    "No Internet Connection Available",
                                    true
                                )
                            }
                            is UIState.SUCCESS -> {
                                try {
                                    //customSnackBar(mBinding.root,"Success",false)
                                    mBinding.progressCircular.customProgressbar.visibility =
                                        View.GONE
                                    val result = resultUIState.data as List<PopularMovieItem>
                                    //bindData(result)
                                    toPopularListDto(result)
                                } catch (e: Exception) {
                                    Log.e(TAG, "onViewCreated: ${e.message}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun toPopularListDto(result: List<PopularMovieItem>) {
        val popularListDto: List<PopularListDto> = result.map { it.toPopularListDto() }
        Log.d(TAG, "toPopularListDto: DTO List $popularListDto")
        mAdapter.updatePopularListItems(popularListDto)
    }


    private fun collectTVShowsList() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                tvShowViewModel.tvShowList.collect { resultUIState ->

                    when (resultUIState) {
                        is UIState.Error -> {
                            mBinding.progressCircular2
                                .customProgressbar.visibility = View.GONE
                            customSnackBar(mBinding.root, "${resultUIState.errorMsg}", true)
                            Log.e(TAG, "onCreate: error")
                        }
                        is UIState.Exception -> {
                            customSnackBar(mBinding.root, resultUIState.message, true)
                            mBinding.progressCircular2
                                .customProgressbar.visibility = View.GONE
                            Log.e(TAG, "onCreate: Exception")
                        }
                        UIState.Ideal -> {}
                        UIState.Loading -> {
                            mBinding.progressCircular2
                                .customProgressbar.visibility = View.VISIBLE
                        }

                        UIState.NoInternet -> {
                            customSnackBar(mBinding.root, "No Internet Connection Available", true)
                        }
                        is UIState.SUCCESS -> {
                            try {
                                mBinding.progressCircular2
                                    .customProgressbar.visibility = View.GONE
                                val result = resultUIState.data as PopularTVResult
                                bindTVShowData(result.results)
                            } catch (e: Exception) {
                                Log.e(TAG, "onViewCreated: ${e.message}")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun bindTVShowData(result: List<PopularTVShow>) {
        val popularListDto: List<PopularListDto> = result.map { it.toPopularListDto() }
        Log.d(TAG, "collectTVShowsList: Result TV Shows $popularListDto")
        tvShowListAdapter.updatePopularListItems(popularListDto)
    }

    private fun bindData(popularMovies: List<PopularMovieItem>) {
        //mAdapter.updatePopularListItems(popularMovies)
    }
}