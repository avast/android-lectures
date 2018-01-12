package com.avast.android.githubbrowser.extensions

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by lukas on 12/21/17.
 */
fun <T> retrofitCallback(success: (Response<T>) -> Unit, failure: (t: Throwable) -> Unit): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) = success(response)
        override fun onFailure(call: Call<T>, t: Throwable) = failure(t)
    }
}