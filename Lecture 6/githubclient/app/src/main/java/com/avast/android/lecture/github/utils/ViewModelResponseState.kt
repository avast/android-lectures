package com.avast.android.lecture.github.utils

sealed class ViewModelResponseState<out T, out E> {
    object Idle: ViewModelResponseState<Nothing, Nothing>()
    object Loading: ViewModelResponseState<Nothing, Nothing>()
    data class Error<out E>(val error: E): ViewModelResponseState<Nothing, E>()
    data class Success<out T>(val content: T): ViewModelResponseState<T, Nothing>()
}