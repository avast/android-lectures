package mff.mdp.demoapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mff.mdp.demoapp.R
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

    val userDetailState by viewModel.userDetails.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val userState = userDetailState) {
            is ViewModelResponseState.Idle -> Unit // Do nothing here
            is ViewModelResponseState.Success ->
                UserCard(user = userState.content)

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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(name = "avast", viewModel = viewModel())
}