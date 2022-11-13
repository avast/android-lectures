package com.avast.mff.lecture3.data

/**
 * Github repository details.
 */
data class GithubRepository(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: User,
    val private: Boolean,
    val description: String = "",
)