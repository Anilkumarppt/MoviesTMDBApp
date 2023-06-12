package com.impressico.moviesapp.presentation.viewmodels

import android.os.Build
import android.provider.Contacts.Intents.UI
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impressico.moviesapp.data.NetworkConstants
import com.impressico.moviesapp.data.remote.NetworkResult

import com.impressico.moviesapp.domain.repository.PopularTVShowRepo
import com.impressico.moviesapp.presentation.states.UIState
import com.impressico.moviesapp.presentation.util.NetworkCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularTVShowViewModel @Inject constructor(private val popularTVShowRepo: PopularTVShowRepo) :
    ViewModel()  {

        private val _tvShowList: MutableStateFlow<UIState> = MutableStateFlow(UIState.Ideal)
        val tvShowList= _tvShowList.asStateFlow()

       private val _tvShowDetail : MutableStateFlow<UIState> = MutableStateFlow(UIState.Ideal)
       val tvShowDetail=_tvShowDetail.asStateFlow()

     @Inject
     lateinit var networkCheck: NetworkCheck
      @RequiresApi(Build.VERSION_CODES.M)
      fun getTVShowList(){
          if(networkCheck.isInternetAvailable()) {

              viewModelScope.launch {
                  _tvShowList.value = UIState.Loading
                  delay(2000)
                  popularTVShowRepo.getPopularTVShows().collect { tvShowList ->
                      when (tvShowList) {
                          is NetworkResult.ApiError -> {
                              _tvShowList.value =
                                  UIState.Error(tvShowList.code, tvShowList.data?.status_message)
                          }
                          is NetworkResult.ApiException -> {
                              _tvShowList.value = UIState.Exception(tvShowList.e.message!!)
                          }
                          is NetworkResult.ApiSuccess -> {
                              var result=tvShowList.data
                              val popularTVShows=result.results
                              popularTVShows.map {tvShow->
                                  tvShow.poster_path =
                                      NetworkConstants.BACKGROUND_BASE_URL + tvShow.poster_path
                                  tvShow.backdrop_path =
                                      NetworkConstants.BACKGROUND_BASE_URL+ tvShow.backdrop_path
                              }
                              _tvShowList.value = UIState.SUCCESS(popularTVShows)
                          }
                      }
                  }
              }
          }
          else
              _tvShowList.value=UIState.NoInternet

      }
    @RequiresApi(Build.VERSION_CODES.M)
    fun getTvShowDetail(tvShowId:Int) {
        if (networkCheck.isInternetAvailable()) {
            viewModelScope.launch {
                _tvShowDetail.value = UIState.Loading
                popularTVShowRepo.getPopularTVShowDetails(tvShowId).collect { tvShowDetail ->
                    when (tvShowDetail) {
                        is NetworkResult.ApiError -> {
                            _tvShowDetail.value =
                                UIState.Error(tvShowDetail.code, tvShowDetail.data?.status_message)
                        }
                        is NetworkResult.ApiException -> {
                            _tvShowDetail.value = UIState.Exception(tvShowDetail.e.message!!)
                        }
                        is NetworkResult.ApiSuccess -> {
                            _tvShowDetail.value = UIState.SUCCESS(tvShowDetail.data)
                        }
                    }
                }
            }
        }
        else
            _tvShowDetail.value=UIState.NoInternet
    }
}