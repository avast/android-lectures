package com.gendigital.mff.lecture7.utils

/**
 * Represents state of fetching data in [androidx.lifecycle.ViewModel].
 */
sealed class ViewModelResponseState<out T, out E> {

    /**
     * Represents idle state, doesn't hold any additional data.
     */
    data object Idle: ViewModelResponseState<Nothing, Nothing>()

    /**
     * Represents load in progress state, doesn't hold any additional data.
     */
    data object Loading: ViewModelResponseState<Nothing, Nothing>()

    /**
     * Represents loading failed with error, holds information about the error.
     */
    data class Error<out E>(val error: E): ViewModelResponseState<Nothing, E>()

    /**
     * Represents data were successfully loaded, holds the loaded data.
     */
    data class Success<out T>(val content: T): ViewModelResponseState<T, Nothing>()
}