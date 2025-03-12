package com.github.mikan.ssot.sample.trend.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.github.mikan.ssot.sample.designsystem.SSoTTheme
import com.github.mikan.ssot.sample.designsystem.StarButton
import com.github.mikan.ssot.sample.trend.TrendQuery

@Composable
fun TrendSection(
    uiState: TrendUiState,
    onClick: (owner: String, name: String) -> Unit,
    onClickAdd: (String) -> Unit,
    onClickRemove: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        uiState.content?.search?.edges?.mapNotNull { it?.node?.onRepository }?.let { edges ->
            TrendSection(
                trends = edges,
                onClick = onClick,
                onClickAdd = onClickAdd,
                onClickRemove = onClickRemove,
            )
        }
        uiState.error?.message?.let {
            Text("Error: $it")
        }
    }
}

@Composable
private fun TrendSection(
    trends: List<TrendQuery.OnRepository>,
    onClick: (owner: String, name: String) -> Unit,
    onClickAdd: (String) -> Unit,
    onClickRemove: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier,
    ) {
        items(
            items = trends,
            key = { it.id },
        ) { repo ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                    onClick = {
                        onClick(repo.owner.login, repo.name)
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = repo.name,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 32.dp),
                    )
                }
                Spacer(Modifier.width(8.dp))

                var hasStarred by remember { mutableStateOf(repo.viewerHasStarred) }
                LaunchedEffect(repo.viewerHasStarred) {
                    hasStarred = repo.viewerHasStarred
                }

                StarButton(
                    hasStarred = hasStarred,
                    onClick = {
                        if (hasStarred) {
                            onClickRemove(repo.id)
                        } else {
                            onClickAdd(repo.id)
                        }
                        hasStarred = !hasStarred
                    }
                )
            }
        }
    }
}


// Preview
@Preview
@Composable
private fun TrendSectionPreview() {
    SSoTTheme {
        Surface(Modifier.fillMaxWidth()) {
            TrendSection(
                trends = listOf(
                    TrendQuery.OnRepository(
                        id = "1",
                        name = "Repo 1",
                        owner = TrendQuery.Owner("Owner 1"),
                        viewerHasStarred = false
                    ),
                    TrendQuery.OnRepository(
                        id = "2",
                        name = "Repo 2",
                        owner = TrendQuery.Owner("Owner 2"),
                        viewerHasStarred = true
                    )
                ),
                onClick = { _, _ -> },
                onClickAdd = {},
                onClickRemove = {},
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
