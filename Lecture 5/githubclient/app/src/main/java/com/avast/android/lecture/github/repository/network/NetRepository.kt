package com.avast.android.lecture.github.repository.network

class NetRepository  {

}


sealed class Result<out T> {
    data class Success<out T>(val value: T): Result<T>()
    data class GenericError(val exception: Exception): Result<Nothing>()
    data class NetworkError(val exception: Exception): Result<Nothing>()
}