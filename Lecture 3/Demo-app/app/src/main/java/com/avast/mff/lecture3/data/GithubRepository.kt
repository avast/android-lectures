package com.avast.mff.lecture3.data


data class GithubRepository(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: User,
    val private: Boolean,
    val description: String = "",
)