package mff.mdp.demoapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mff.mdp.demoapp.R
import mff.mdp.demoapp.ui.theme.GithubAppTheme
import mff.mdp.demoapp.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToProfile: (String) -> Unit
) {
    val context = LocalContext.current
    val lastUsername by viewModel.getLastUsername(context).collectAsState("")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.home_screen_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.padding(4.dp))

            var searchedUser: String? by rememberSaveable { mutableStateOf(null) }

            OutlinedTextField(
                value = searchedUser ?: lastUsername.orEmpty(),
                onValueChange = { searchedUser = it },
                label = { Text(stringResource(R.string.search_user_prompt)) },
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Button(
                onClick = {
                    val username = searchedUser ?: lastUsername.orEmpty()
                    viewModel.saveLastUsername(context, username)
                    navigateToProfile(username)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(R.string.go_to_profile))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GithubAppTheme {
        HomeScreen(
            viewModel = HomeViewModel(),
            navigateToProfile = {}
        )
    }
}
