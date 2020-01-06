package com.avast.android.githubclient.lecture5.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubRepository(val id: Int,
                            val name: String,
                            val full_name: String,
                            val owner: User,
                            val private: Boolean,
                            val description: String? = "",
                            val stargazers_count: Int = 0)