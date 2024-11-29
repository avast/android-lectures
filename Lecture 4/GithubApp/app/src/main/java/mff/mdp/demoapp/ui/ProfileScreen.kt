package mff.mdp.demoapp.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import mff.mdp.demoapp.R
import mff.mdp.demoapp.data.GithubRepository
import mff.mdp.demoapp.data.User
import mff.mdp.demoapp.viewmodels.ProfileViewModel
import mff.mdp.demoapp.viewmodels.ViewModelResponseState

@Composable
fun ProfileScreen(
    name: String,
    viewModel: ProfileViewModel
) {
    LaunchedEffect(name) {
        Log.d("ProfileScreen", "Loading user details for $name")
        viewModel.loadUserDetails(name)
    }

    val userDetailState by viewModel.userDetails.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val userState = userDetailState) {
            is ViewModelResponseState.Idle -> Unit // Do nothing here
            is ViewModelResponseState.Success ->
                Column {
                    UserCard(user = userState.content.user)
                    RepositoriesList(repositories = userState.content.repositories)
                }

            is ViewModelResponseState.Error ->
                Text(
                    text = stringResource(R.string.profile_error_message, userState.error),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 24.dp)
                )

            ViewModelResponseState.Loading ->
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun UserCard(user: User, modifier: Modifier = Modifier) {
    UserCard(
        name = user.login,
        url = user.html_url,
        imageUrl = user.avatar_url,
        modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepositoriesList(repositories: List<GithubRepository>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.repositories_header),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(repositories.size) { index ->
                Item(
                    title = repositories[index].name,
                    subtitle = repositories[index].description,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(name = "avast", viewModel = viewModel())
}
