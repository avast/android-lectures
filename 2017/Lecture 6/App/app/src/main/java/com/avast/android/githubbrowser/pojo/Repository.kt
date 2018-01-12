package com.avast.android.githubbrowser.pojo

/**
 * Created by lukas on 12/21/17.
 */
data class Repository (
        val id: Int,
        val name: String,
        val fullName: String,
        val description: String,
        val fork: Boolean,
        val owner: User
)