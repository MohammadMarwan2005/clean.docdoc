package com.alaishat.mohammad.clean.docdoc.presentation.common.reusable

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alaishat.mohammad.clean.docdoc.presentation.theme.BottomNavBarContainerColor

/**
 * Created by Mohammad Al-Aishat on Apr/16/2025.
 * Clean DocDoc Project.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.MyRefreshIndicator(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    pullToRefreshState: PullToRefreshState
) {
    Indicator(
        modifier = Modifier.align(Alignment.TopCenter).then(modifier),
        isRefreshing = isRefreshing,
        state = pullToRefreshState,
        containerColor = BottomNavBarContainerColor,
        color = MaterialTheme.colorScheme.primary
    )
}
