package com.impressico.moviesapp.presentation.viewmodels

import android.provider.Contacts.Intents.UI
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impressico.moviesapp.data.remote.NetworkResult

import com.impressico.moviesapp.domain.repository.PopularTVShowRepo
import com.impressico.moviesapp.presentation.states.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
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

      fun getTVShowList(){
          viewModelScope.launch {
              _tvShowList.value=UIState.Loading
              popularTVShowRepo.getPopularTVShows().collect{tvShowList->
                  when(tvShowList){
                      is NetworkResult.ApiError -> {
                          _tvShowList.value=UIState.Error(tvShowList.code,tvShowList.data?.status_message)
                      }
                      is NetworkResult.ApiException -> {
                          _tvShowList.value=UIState.Exception(tvShowList.e.message!!)
                      }
                      is NetworkResult.ApiSuccess -> {
                          _tvShowList.value=UIState.SUCCESS(tvShowList.data)
                      }
                  }
              }
          }

          fun getTvShowDetail(tvShowId:Int){
              viewModelScope.launch {
                  _tvShowDetail.value=UIState.Loading
                  popularTVShowRepo.getPopularTVShowDetails(tvShowId).collect{tvShowDetail->
                      when(tvShowDetail){
                          is NetworkResult.ApiError -> {
                              _tvShowDetail.value=UIState.Error(tvShowDetail.code,tvShowDetail.data?.status_message)
                          }
                          is NetworkResult.ApiException -> {
                              _tvShowDetail.value=UIState.Exception(tvShowDetail.e.message!!)
                          }
                          is NetworkResult.ApiSuccess -> {
                              _tvShowDetail.value=UIState.SUCCESS(tvShowDetail.data)
                          }
                      }
                  }
              }
          }
      }
}