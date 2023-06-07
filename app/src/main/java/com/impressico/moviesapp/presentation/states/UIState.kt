package com.impressico.moviesapp.presentation.states

sealed class UIState(){

    data class SUCCESS(val data:Any): UIState()
    object Loading : UIState()
    data class Error(val errorCode:Int,val errorMsg:String? ): UIState()
    data class Exception(val message:String):UIState()
    object Ideal:UIState()
    object NoInternet: UIState()
}
