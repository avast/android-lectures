package com.avast.android.lecture.github.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRepository(val id: Int,
                            @SerialName("name") val name: String,
                            val full_name: String,
                            val owner: User,
                            val private: Boolean,
                            val description: String = "")