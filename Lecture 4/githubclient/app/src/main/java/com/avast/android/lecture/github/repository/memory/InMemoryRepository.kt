package com.avast.android.lecture.github.repository.memory

import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.Repository

/**
 * Static mock implementation of github data.
 */
class InMemoryRepository: Repository {

    override fun getUser(username: String): User? {
        return when (username) {
            "avast" -> User(
                    login = "avast",
                    id = 3996079,
                    avatar_url = "https://avatars1.githubusercontent.com/u/3996079?v=4",
                    url = "https://api.github.com/users/avast",
                    html_url = "https://github.com/avast",
                    followers_url = "https://api.github.com/users/avast/followers",
                    following_url = "https://api.github.com/users/avast/following{/other_user}",
                    followers = 0,
                    following = 0,
                    public_gists = 0,
                    public_repos = 98)
            "inmite" -> User(
                    login = "inmite",
                    id = 3819616,
                    avatar_url = "https://avatars1.githubusercontent.com/u/3819616?v=4",
                    url = "https://api.github.com/users/inmite",
                    html_url = "https://github.com/inmite",
                    followers_url = "https://api.github.com/users/inmite/followers",
                    following_url = "https://api.github.com/users/inmite/following{/other_user}",
                    followers = 0,
                    following = 0,
                    public_gists = 0,
                    public_repos = 10)
            "square" -> User(
                    login = "square",
                    id = 82592,
                    avatar_url = "https://avatars0.githubusercontent.com/u/82592?v=4",
                    url = "https://api.github.com/users/square",
                    html_url = "https://github.com/square",
                    followers_url = "https://api.github.com/users/square/followers",
                    following_url = "https://api.github.com/users/square/following{/other_user}",
                    followers = 0,
                    following = 0,
                    public_gists = 5,
                    public_repos = 223)
            else -> null
        }
    }

    override fun getUserRepository(username: String): List<GithubRepository> {
        val user = getUser(username)
        return user?.let {
            return when (username) {
                "avast" -> listOf(
                        GithubRepository(
                                id = 15397085,
                                name = "android-butterknife-zelezny",
                                description = "Android Studio plug-in for generating ButterKnife injections from selected layout XML.",
                                full_name = "avast/android-butterknife-zelezny",
                                owner = it,
                                private = false),
                        GithubRepository(id = 45212866,
                                name = "android-lectures",
                                description = "Class material for lectures about Android development",
                                full_name = "avast/android-lectures",
                                owner = it,
                                private = false),
                        GithubRepository(id = 10281119,
                                name = "android-styled-dialogs",
                                description = "Class material for lectures about Android development",
                                full_name = "avast/android-styled-dialogs",
                                owner = it,
                                private = false))
                "inmite" -> listOf(
                        GithubRepository(
                                id = 13663078,
                                name = "android-validation-komensky",
                                description = "A simple library for validating user input in forms using annotations.",
                                full_name = "inmite/android-validation-komensky",
                                owner = it,
                                private = false),
                        GithubRepository(id = 12222625,
                                name = "android-selector-chapek",
                                description = "Android Studio plugin which automatically generates drawable selectors from appropriately named resources.",
                                full_name = "inmite/android-selector-chapek",
                                owner = it,
                                private = false),
                        GithubRepository(id = 10281119,
                                name = "android-grid-wichterle",
                                description = "This app will show grid overlay over whole system which helps you to verify your excellent app design.",
                                full_name = "inmite/android-grid-wichterle",
                                owner = it,
                                private = false))
                "square" -> listOf(
                        GithubRepository(id = 5152285,
                                name = "okhttp",
                                description = "An HTTP+HTTP/2 client for Android and Java applications.",
                                full_name = "square/okhttp",
                                owner = it,
                                private = false),
                        GithubRepository(id = 892275,
                                name = "retrofit",
                                description = "Type-safe HTTP client for Android and Java by Square, Inc.",
                                full_name = "square/retrofit",
                                owner = it,
                                private = false)
                )
                else -> emptyList()
            }

        } ?: emptyList()
    }
}