package com.github.mikan.ssot.sample.repositorydetail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mikan.ssot.sample.designsystem.SSoTTheme
import com.github.mikan.ssot.sample.designsystem.StarButton
import com.github.mikan.ssot.sample.repositorydetail.RepositoryDetailQuery
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDetailRoute(
    val owner: String,
    val name: String,
)

@Composable
fun RepositoryDetailScreen(
    viewModel: RepositoryDetailViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(modifier = modifier) {
        uiState.content?.repository?.let {
            RepositoryDetailScreen(
                repository = it,
                onClickAdd = viewModel::addStar,
                onClickRemove = viewModel::removeStar,
            )
        }
        uiState.error?.message?.let {
            Text("Error: $it")
        }
    }
}

@Composable
private fun RepositoryDetailScreen(
    repository: RepositoryDetailQuery.Repository,
    onClickAdd: (String) -> Unit,
    onClickRemove: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var hasStarred by remember { mutableStateOf(repository.viewerHasStarred) }
    LaunchedEffect(repository.viewerHasStarred) {
        hasStarred = repository.viewerHasStarred
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = repository.nameWithOwner,
            style = MaterialTheme.typography.headlineSmall,
        )
        repository.description?.let {
            Text(it)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            StarButton(
                hasStarred = hasStarred,
                onClick = {
                    if (hasStarred) {
                        onClickRemove(repository.id)
                    } else {
                        onClickAdd(repository.id)
                    }
                    hasStarred = !hasStarred
                }
            )
            Text("${repository.stargazerCount} Star")
        }
        repository.homepageUrl?.let {
            Button({}) {
                Text("Homepage")
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryDetailScreenPreview() {
    SSoTTheme {
        Surface {
            RepositoryDetailScreen(
                repository = RepositoryDetailQuery.Repository(
                    id = "1",
                    nameWithOwner = "mikan/ssot",
                    description = "Sample app for Single Source of Truth",
                    homepageUrl = "https://example.com",
                    viewerHasStarred = true,
                    stargazerCount = 100,
                    issues = RepositoryDetailQuery.Issues(
                        totalCount = 10
                    ),
                    pullRequests = RepositoryDetailQuery.PullRequests(
                        totalCount = 100
                    )
                ),
                onClickAdd = {},
                onClickRemove = {}
            )
        }
    }
}
