package com.avast.android.lecture.github.data

data class User(val login: String,
                val id: Int,
                val avatar_url: String,
                val gravatar_id: String = "",
                val url: String,
                val html_url: String,
                val followers_url: String,
                val following_url: String,
                val public_repos: Int = 0,
                val public_gists: Int = 0,
                val followers: Int = 0,
                val following: Int = 0)