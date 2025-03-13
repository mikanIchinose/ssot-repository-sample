package com.github.mikan.ssot.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mikan.ssot.sample.myaccount.ui.MyAccountSection
import com.github.mikan.ssot.sample.myaccount.ui.MyAccountUiState
import com.github.mikan.ssot.sample.myaccount.ui.MyAccountViewModel
import com.github.mikan.ssot.sample.repositorydetail.ui.RepositoryDetailRoute
import com.github.mikan.ssot.sample.trend.ui.TrendSection
import com.github.mikan.ssot.sample.trend.ui.TrendUiState
import com.github.mikan.ssot.sample.trend.ui.TrendViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Composable
fun HomeScreen(
    navigateToRepositoryDetail: (RepositoryDetailRoute) -> Unit,
    modifier: Modifier = Modifier,
    myAccountViewModel: MyAccountViewModel = hiltViewModel(),
    trendViewModel: TrendViewModel = hiltViewModel(),
) {
    val myAccountUiState by myAccountViewModel.uiState.collectAsStateWithLifecycle()
    val trendUiState by trendViewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    HomeScreen(
        myAccountUiState = myAccountUiState,
        trendUiState = trendUiState,
        navigateToRepositoryDetail = navigateToRepositoryDetail,
        onClickAdd = {
            scope.launch {
                trendViewModel.addStar(it)
                myAccountViewModel.refresh()
            }
        },
        onClickRemove = {
            scope.launch {
                trendViewModel.removeStar(it)
                myAccountViewModel.refresh()
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    myAccountUiState: MyAccountUiState,
    trendUiState: TrendUiState,
    navigateToRepositoryDetail: (RepositoryDetailRoute) -> Unit,
    onClickAdd: (String) -> Unit,
    onClickRemove: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.padding(horizontal = 16.dp)
    ) {
        MyAccountSection(myAccountUiState)
        TrendSection(
            uiState = trendUiState,
            onClick = { owner, name ->
                navigateToRepositoryDetail(RepositoryDetailRoute(owner, name))
            },
            onClickAdd = onClickAdd,
            onClickRemove = onClickRemove,
        )
    }
}
