package org.mireiavh.finalproject.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.mireiavh.finalproject.AuthManager
import org.mireiavh.finalproject.presentation.CharacterTabView
import org.mireiavh.finalproject.presentation.DiceView
import org.mireiavh.finalproject.presentation.HomeView
import org.mireiavh.finalproject.utils.CustomBottomNavigationItem
import org.mireiavh.finalproject.utils.CustomDrawerConfigurationSection
import org.mireiavh.finalproject.utils.CustomDrawerDevsConfigSection
import org.mireiavh.finalproject.utils.CustomDrawerSignOutSection
import org.mireiavh.finalproject.utils.CustomDrawerSuportSection
import org.mireiavh.finalproject.utils.CustomDrawerUserNameNImage
import org.mireiavh.finalproject.utils.CustomDrawerUserTextProfile
import org.mireiavh.finalproject.utils.CustomTopBar
import org.mireiavh.finalproject.utils.DarkBrown

@Serializable
object HomeSection

@Serializable
object DiceSection

@Serializable
object CharacterTabSection

data class TopLevelRoute<T : Any> (
    val name : String,
    val route : T,
    val icon : ImageVector
)

val topLevelRoutes = listOf(
    TopLevelRoute("Manuales", HomeSection, Icons.Default.Home),
    TopLevelRoute("Dados", DiceSection, Icons.Default.Add),
    TopLevelRoute("Personajes", CharacterTabSection, Icons.Default.AccountBox)
)


@Composable
fun menuNavigation(
    onSignOutClick: () -> Unit,
    auth: AuthManager
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = topLevelRoutes.find { route ->
        currentDestination?.hierarchy?.any { it.route == route.route } == true
    }

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBrown)
                    .padding(16.dp)
            ) {
                CustomDrawerUserTextProfile()
                CustomDrawerUserNameNImage(auth)
                CustomDrawerConfigurationSection()
                CustomDrawerSuportSection()
                CustomDrawerDevsConfigSection()

                Spacer(modifier = Modifier.weight(1f))
                CustomDrawerSignOutSection(onSignOutClick)
            }
        },
        content = {
            Scaffold(
                topBar = { CustomTopBar(text = currentRoute?.name ?: "", onClick = { coroutineScope.launch { drawerState.open() } }) },
                bottomBar = {
                    BottomNavigation(backgroundColor = Color(0xFF800000), elevation = 8.dp) {
                        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
                        topLevelRoutes.forEach { topLevelRoute ->
                            CustomBottomNavigationItem(
                                route = topLevelRoute,
                                isSelected = currentDestination?.hierarchy?.any {
                                    it.route == topLevelRoute.route
                                } == true,
                                onClick = {
                                    navController.navigate(topLevelRoute.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )}}
                }
            ) { innerPadding ->
                NavHost(navController, startDestination = HomeSection, modifier = Modifier.padding(innerPadding)
                ) {
                    composable<HomeSection> { HomeView() }
                    composable<DiceSection> { DiceView() }
                    composable<CharacterTabSection> { CharacterTabView() }
                }
            }

        }
    )
}

