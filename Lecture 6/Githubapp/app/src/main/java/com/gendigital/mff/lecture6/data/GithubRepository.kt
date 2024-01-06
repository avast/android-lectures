package com.gendigital.mff.lecture6.data

import kotlinx.serialization.Serializable

/**
 * Github repository details.
 */
@Serializable
data class GithubRepository(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: User,
    val private: Boolean,
    val description: String = "",
)