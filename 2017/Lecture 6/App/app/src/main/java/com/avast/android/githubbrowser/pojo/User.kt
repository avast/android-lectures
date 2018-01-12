package com.avast.android.githubbrowser.pojo

/**
 * Created by lukas on 12/21/17.
 */
data class User(
        val login: String,
        val id: Int,
        val avatarUrl: String,
        val name: String,
        val company: String
)