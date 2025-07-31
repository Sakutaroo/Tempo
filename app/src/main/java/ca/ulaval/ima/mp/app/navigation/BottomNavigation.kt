package ca.ulaval.ima.mp.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray800
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950

val bottomBarScreens = listOf(
    Screens.FocusScreen, Screens.ProfileScreen
)

data class BottomNavigationItem<T: Any>(
    val title: String,
    val route: T,
    val selectedItem: ImageVector,
    val unselectedItem: ImageVector
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = "Focus",
        route = Screens.FocusScreen,
        selectedItem = Icons.Filled.RocketLaunch,
        unselectedItem = Icons.Outlined.RocketLaunch
    ),
    BottomNavigationItem(
        title = "Profile",
        route = Screens.ProfileScreen,
        selectedItem = Icons.Filled.Person,
        unselectedItem = Icons.Outlined.Person
    )
)

@Composable
fun TempoBottomNavigation(
    items: List<BottomNavigationItem<*>>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    currentRoute: String? = null
) {
    val index = bottomBarScreens.indexOfFirst {
        it::class.qualifiedName == currentRoute
    }
    var selectedItem by rememberSaveable {
        mutableIntStateOf(index.coerceAtLeast(0))
    }

    NavigationBar(
        modifier = modifier,
        containerColor = Gray50,
        contentColor = Gray950
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    if (selectedItem != index) {
                        selectedItem = index
                        navController.navigate(item.route) {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selectedItem == index) {
                            item.selectedItem
                        } else {
                            item.unselectedItem
                        },
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Gray950,
                    unselectedIconColor = Gray800,
                    selectedTextColor = Gray950,
                    unselectedTextColor = Gray800,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
