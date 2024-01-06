package com.gendigital.mff.lecture7.repository.network

import io.ktor.resources.Resource

@Resource("/users")
class GithubApi {

    @Resource("{username}")
    class User(val parent: GithubApi = GithubApi(), val username: String) {

        @Resource("repos")
        class Repositories(val parent: User)
    }
}