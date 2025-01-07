package mff.mdp.demoapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    navigateToProfile: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = "This is a home screen",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = Bold,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.padding(12.dp))

            Button(onClick = {
                navigateToProfile("John Doe")
            },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Go to profile")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navigateToProfile = {}
    )
}
