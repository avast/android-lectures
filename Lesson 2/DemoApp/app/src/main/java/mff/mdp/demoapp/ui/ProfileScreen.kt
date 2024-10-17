package mff.mdp.demoapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mff.mdp.demoapp.R

@Composable
fun ProfileScreen(name: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        UserCard(
            name = name,
            url = "https://developer.android.com",
            imageRes = R.drawable.ic_launcher_foreground,
        )
    }
}
