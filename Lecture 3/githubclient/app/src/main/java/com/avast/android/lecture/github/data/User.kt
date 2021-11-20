package com.avast.android.lecture.github.data

/**
 * Represents github user.
 *
 * @param login username.
 * @param id user id.
 * @param avatar_url avatar url.
 * @param gravatar_id
 * @param url Url of the API of the user.
 * @param html_url Url with link to profile page.
 * @param followers_url Url with the list of followers.
 * @param following_url Url with the list of following users.
 * @param public_repos Count of public repositories.
 * @param public_gists Count of public gists.
 * @param followers Count of followers.
 * @param following Count of users, current user follows.
 */
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