package com.avast.android.lecture.github.data

import kotlinx.serialization.Serializable

@Serializable
data class GithubRepository(val id: Int,
                            val name: String,
                            val full_name: String,
                            val owner: User,
                            val private: Boolean,
                            val description: String = "")