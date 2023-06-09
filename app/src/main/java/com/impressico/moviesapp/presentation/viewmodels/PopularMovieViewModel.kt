package com.impressico.moviesapp.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impressico.moviesapp.data.remote.NetworkResult
import com.impressico.moviesapp.domain.repository.PopularMovieRepo
import com.impressico.moviesapp.presentation.states.UIState
import com.impressico.moviesapp.presentation.util.NetworkCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(private val popularMovieRepo: PopularMovieRepo) :ViewModel() {

    private val _popularMoviesList:MutableStateFlow<UIState> = MutableStateFlow(UIState.Ideal)
    val popularMovieList=_popularMoviesList.asStateFlow()

    private val _popularMovieItem:MutableStateFlow<UIState> = MutableStateFlow(UIState.Ideal)
    val popularMovieItem=_popularMovieItem.asStateFlow()

    @Inject
    lateinit var networkCheck: NetworkCheck
    @RequiresApi(Build.VERSION_CODES.M)
    fun getPopularMovies(){
        if (networkCheck.isInternetAvailable()) {
            viewModelScope.launch {
                    _popularMoviesList.value = UIState.Loading
                    popularMovieRepo.getPopularMovies().collect { popularMoviesResult ->
                        when (popularMoviesResult) {
                            is NetworkResult.ApiError -> {
                                _popularMoviesList.value = UIState.Error(
                                    popularMoviesResult.code,
                                    popularMoviesResult.data?.status_message
                                )
                            }
                            is NetworkResult.ApiException -> {
                                _popularMoviesList.value =
                                    UIState.Exception(popularMoviesResult.e.message!!)
                            }
                            is NetworkResult.ApiSuccess -> {
                                val result = popularMoviesResult.data
                                var popularMovieList = result.popularMovies
                                popularMovieList.map { movie ->
                                    movie.poster_path =
                                        "https://image.tmdb.org/t/p/w500" + movie.poster_path
                                    movie.backdrop_path =
                                        "https://image.tmdb.org/t/p/w500" + movie.backdrop_path
                                }

                                _popularMoviesList.value = UIState.SUCCESS(popularMovieList)
                            }
                        }
                    }
                }
            }
        else
            _popularMoviesList.value=UIState.NoInternet
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun getMovieDetails(movieId:Int){
        if(networkCheck.isInternetAvailable()) {
            viewModelScope.launch {
                _popularMoviesList.value = UIState.Loading
                popularMovieRepo.getPopularMovieDetails(movieId).collect { popularMoviesResult ->
                    when (popularMoviesResult) {
                        is NetworkResult.ApiError -> {
                            _popularMovieItem.value = UIState.Error(
                                popularMoviesResult.code,
                                popularMoviesResult.data?.status_message
                            )
                        }
                        is NetworkResult.ApiException -> {
                            _popularMovieItem.value =
                                UIState.Exception(popularMoviesResult.e.message!!)
                        }
                        is NetworkResult.ApiSuccess -> {
                            val result = popularMoviesResult.data
                            _popularMovieItem.value = UIState.SUCCESS(result)
                        }
                    }
                }
            }
        }
        else
            _popularMoviesList.value=UIState.NoInternet
    }
}