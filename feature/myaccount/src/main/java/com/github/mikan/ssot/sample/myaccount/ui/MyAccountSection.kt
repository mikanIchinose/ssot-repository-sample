package com.github.mikan.ssot.sample.myaccount.ui

import android.icu.text.NumberFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.github.mikan.ssot.sample.designsystem.SSoTTheme
import com.github.mikan.ssot.sample.myaccount.MyAccountQuery

@Composable
fun MyAccountSection(
    uiState: MyAccountUiState,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        uiState.content?.user?.let {
            MyAccountSection(it)
        }
        uiState.error?.message?.let {
            Text("Error: $it")
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun MyAccountSection(
    user: MyAccountQuery.User,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.LightGray,
        ),
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            val previewHandler = AsyncImagePreviewHandler {
                ColorImage(Color.Red.toArgb())
            }
            CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    "${user.name}",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        NumberFormat.getInstance().format(user.starredRepositories.totalCount),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
        }
    }
}

// preview
@Preview
@Composable
private fun MyAccountSectionPreview() {
    SSoTTheme {
        Surface(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            MyAccountSection(
                MyAccountQuery.User(
                    name = "mikan",
                    avatarUrl = "",
                    starredRepositories = MyAccountQuery.StarredRepositories(1000),
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}
