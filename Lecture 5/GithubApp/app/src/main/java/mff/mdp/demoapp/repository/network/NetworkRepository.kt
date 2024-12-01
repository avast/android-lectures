package mff.mdp.demoapp.repository.network

import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mff.mdp.demoapp.data.GithubRepository
import mff.mdp.demoapp.data.User
import mff.mdp.demoapp.repository.Repository

class NetworkRepository: Repository {

    override suspend fun getUser(username: String): Result<User> = runCatching {
        withContext(Dispatchers.IO) {
            Provider.client.get(GithubApi.User(GithubApi(), username)).body<User>()
        }
    }

    override suspend fun getUserRepository(username: String): Result<List<GithubRepository>> = runCatching {
        withContext(Dispatchers.IO) {
            Provider.client.get(GithubApi.User.Repositories(GithubApi.User(GithubApi(), username)))
                .body<List<GithubRepository>>()
        }
    }
}