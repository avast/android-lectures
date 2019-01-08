package com.avast.android.lecture.github.repository.net

import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.Repository

class NetRepository: Repository {

    override fun getUser(username: String): User? {
        return GithubServiceFactory.githubService.getUser(username).execute().body()
    }

    override fun getUserRepository(username: String): List<GithubRepository> {
        return GithubServiceFactory.githubService.getUserRepository(username).execute().body() ?: emptyList()
    }

}