package com.impressico.moviesapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.impressico.moviesapp.domain.repository.PopularMovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val popularMovieRepo: PopularMovieRepo):ViewModel() {


}