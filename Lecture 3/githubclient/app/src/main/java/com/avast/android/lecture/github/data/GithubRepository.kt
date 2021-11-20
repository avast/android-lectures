package com.avast.android.lecture.github.data

/**
 * Represents GitHub repository.
 *
 * @param id Repository id
 * @param name Repository name.
 * @param full_name Name of the repository in form "owner/repository"
 * @param owner User who owns the repository.
 * @param private Flag if the repository is private or not.
 * @param description Optional description of the repository.
 */
data class GithubRepository(val id: Int,
                            val name: String,
                            val full_name: String,
                            val owner: User,
                            val private: Boolean,
                            val description: String = "")