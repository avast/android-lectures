package mff.mdp.demoapp.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mff.mdp.demoapp.R

@Composable
fun UserCard(
    name: String,
    url: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
            ) {

                // Using Coil to asynchronously load the images.
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "User Avatar",
                    modifier = Modifier.size(48.dp),
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
                )

                Text(
                    text = name,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 12.dp),
                    fontWeight = Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Text(
                text = url,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        // Open the URL in a browser
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            Spacer(modifier = Modifier.padding(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    UserCard(
        "Android",
        "https://developer.android.com",
        "https://cdn.iconscout.com/icon/free/png-256/free-android-logo-icon-download-in-svg-png-gif-file-formats--wordmark-programming-langugae-language-pack-logos-icons-1175276.png?f=webp&w=256"
    )
}
