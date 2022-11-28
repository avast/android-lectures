package com.avast.mff.lecture5.repository.network

import com.avast.mff.lecture5.data.GithubRepository
import com.avast.mff.lecture5.data.User
import com.avast.mff.lecture5.di.Provisions
import com.avast.mff.lecture5.repository.Repository
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class NetworkRepository(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

}