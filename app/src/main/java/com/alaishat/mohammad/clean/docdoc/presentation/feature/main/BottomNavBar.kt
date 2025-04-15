package com.alaishat.mohammad.clean.docdoc.presentation.feature.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.NavigationRoute
import com.alaishat.mohammad.clean.docdoc.presentation.theme.BottomNavBarContainerColor

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomNavBar(
    navItems: List<DocDocBottomNavBarItem>,
    selectedItemIndex: Int,
    onItemSelected: (NavigationRoute) -> Unit,
) {
    BottomAppBar(
        containerColor = BottomNavBarContainerColor
    ) {
        navItems.forEachIndexed { index, docDocBottomNavBarItem ->
            if (index == navItems.size / 2)
                Spacer(modifier = Modifier.fillMaxWidth(0.15f))
            NavigationBarItem(
                alwaysShowLabel = false,
                label = {
                    Text(
                        text = stringResource(docDocBottomNavBarItem.titleId),
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = selectedItemIndex == index,
                onClick = { onItemSelected(docDocBottomNavBarItem.route) },
                icon = {
                    BadgedBox(badge = {
                        if (docDocBottomNavBarItem.badgeCount != 0) {
                            Badge(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ) { Text(text = docDocBottomNavBarItem.badgeCount.toString()) }
                        }
                    }) {
                        if (selectedItemIndex == index)
                            Icon(
                                painter = painterResource(id = docDocBottomNavBarItem.selectedIcon),
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                        else
                            Icon(
                                painter = painterResource(id = docDocBottomNavBarItem.unselectedIcon),
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                    }
                }
            )
        }
    }

}

data class DocDocBottomNavBarItem(
    val titleId: Int,
    val route: NavigationRoute,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean = false,
    var badgeCount: Int = 0,
)
