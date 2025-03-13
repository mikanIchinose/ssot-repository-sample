package com.github.mikan.ssot.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikan.ssot.sample.myaccount.domain.MyAccountRepository
import com.github.mikan.ssot.sample.repositorydetail.domain.RepositoryDetailRepository
import com.github.mikan.ssot.sample.trend.data.TrendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val myAccountRepository: MyAccountRepository,
    private val trendRepository: TrendRepository,
    private val repositoryDetailRepository: RepositoryDetailRepository,
) : ViewModel() {
    fun refreshAll() {
        viewModelScope.launch {
            myAccountRepository.refresh()
            trendRepository.refresh()
            repositoryDetailRepository.refresh()
        }
    }
}