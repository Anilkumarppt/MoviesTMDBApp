package com.impressico.moviesapp.data.remote

import com.google.gson.Gson
import com.impressico.moviesapp.data.remote.model.ErrorResponse
import retrofit2.HttpException
import retrofit2.Response

class RemoteDataSource {

    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<T> {
        return try {
            val response = execute()
            val body = response.body()
            var parsedError: ErrorResponse? = null
            // If the response is successful and the body is not null, return an [ApiSuccess] result.
            if (response.isSuccessful && body != null) {
                NetworkResult.ApiSuccess(body)
            } else {
                // If the response is not successful or the body is null, parse the error response and return an [ApiError] result.
                response.errorBody()?.use { error ->
                    parsedError =
                        Gson().fromJson(error.charStream(), ErrorResponse::class.java)
                }
                when (response.code()) {
                    // Handle common HTTP error codes by returning an [ApiError] result with the corresponding status code and
                    // parsed error with Custom Error response other than original api error message.
                    400 -> NetworkResult.ApiError(response.code(), data = parsedError!!)
                    401 -> NetworkResult.ApiError(response.code(), data = parsedError!!.apply {
                        status_message  = "Authentication failed!"
                    })
                    403 -> NetworkResult.ApiError(response.code(), data = parsedError!!)
                    404 -> NetworkResult.ApiError(response.code(), data = parsedError!!)
                    408 -> NetworkResult.ApiError(response.code(), data = parsedError!!)
                    429 -> NetworkResult.ApiError(response.code(), data = parsedError!!)
                    in 500..599 -> NetworkResult.ApiError(response.code(), data = parsedError!!)
                    else -> NetworkResult.ApiError(response.code(), data = parsedError!!)
                }

                //NetworkResult.ApiError(response.code(), data = parsedError!!)
            }
        } catch (e: HttpException) {
            // Handle HTTP exceptions by returning an [ApiError] result
            // with the exception's status code and no data.
            when (e.code()) {
                400 -> NetworkResult.ApiError(e.code(), data = null)
                401 -> NetworkResult.ApiError(e.code(), data = null)
                403 -> NetworkResult.ApiError(e.code(), data = null)
                404 -> NetworkResult.ApiError(e.code(), data = null)
                408 -> NetworkResult.ApiError(e.code(), data = null)
                429 -> NetworkResult.ApiError(e.code(), data = null)
                in 500..599 -> NetworkResult.ApiError(e.code(), data = null)
                else -> NetworkResult.ApiError(e.code(), data = null)
            }
            //NetworkResult.ApiException(e)
        } catch (e: Throwable) {
            // Handle general exceptions by returning an [ApiException] result.
            NetworkResult.ApiException(e)
        } catch (e: Exception) {
            // Handle general exceptions by returning an [ApiException] result.
            NetworkResult.ApiException(e)
        }

    }

}