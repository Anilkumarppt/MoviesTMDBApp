package com.impressico.moviesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impressico.moviesapp.data.remote.NetworkResult
import com.impressico.moviesapp.data.remote.model.PopularMovie
import com.impressico.moviesapp.domain.repository.PopularMovieRepo
import com.impressico.moviesapp.presentation.states.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(private val popularMovieRepo: PopularMovieRepo) :ViewModel() {

    private val _popularMoviesList:MutableStateFlow<UIState> = MutableStateFlow(UIState.Ideal)
    val popularMovieList=_popularMoviesList.asStateFlow()

    fun getPopularMovies(){
            viewModelScope.launch {
                _popularMoviesList.value=UIState.Loading
                popularMovieRepo.getPopularMovies().collect{popularMoviesResult->
                    when(popularMoviesResult){
                        is NetworkResult.ApiError -> {
                            _popularMoviesList.value=UIState.Error(popularMoviesResult.code,popularMoviesResult.data?.status_message)
                        }
                        is NetworkResult.ApiException -> {
                            _popularMoviesList.value=UIState.Exception(popularMoviesResult.e.message!!)
                        }
                        is NetworkResult.ApiSuccess ->{
                            _popularMoviesList.value=UIState.SUCCESS(popularMoviesResult.data)
                        }
                    }
                }
            }
    }
}