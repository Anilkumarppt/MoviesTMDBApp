package com.impressico.recipesapp.data.remote


import com.impressico.recipesapp.data.remote.model.ErrorResponse

/*With this sealed interface subclass don't need to be placed in same package,
* which means we can use the class name as it is.
* However, sealed interfaces must have public visibility for all properties and they can expose unintended API surfaces.*/
sealed interface NetworkResult<out T:Any>{
    data class ApiSuccess<out T:Any>(val data: T):NetworkResult<T>
    data class ApiError(val code:Int,
    val data: ErrorResponse?):NetworkResult<Nothing>
   data class ApiException(val e:Throwable):NetworkResult<Nothing>
}
