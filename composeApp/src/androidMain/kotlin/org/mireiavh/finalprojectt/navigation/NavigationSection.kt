package org.mireiavh.finalprojectt.navigation

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.mireiavh.finalprojectt.AuthManager
import org.mireiavh.finalprojectt.infrastructure.FirebaseManualRepository
import org.mireiavh.finalprojectt.infrastructure.controllers.ManualViewModel
import org.mireiavh.finalprojectt.ui.CharacterTabView
import org.mireiavh.finalprojectt.ui.DiceView
import org.mireiavh.finalprojectt.ui.HomeSection.ManualDetailSection
import org.mireiavh.finalprojectt.ui.HomeView
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.utils.CustomBottomNavigationItem
import org.mireiavh.finalprojectt.utils.CustomDrawerSignOutSection
import org.mireiavh.finalprojectt.utils.CustomModalDrawer
import org.mireiavh.finalprojectt.utils.CustomTopBar
import org.mireiavh.finalprojectt.utils.DarkBrown

@Serializable
object HomeSection

@Serializable
object DiceSection

@Serializable
object CharacterTabSection

val routeMap = mapOf(
    HomeSection to "home",
    DiceSection to "dice",
    CharacterTabSection to "character_tab"
)

data class TopLevelRoute<T : Any>(
    val name: String,
    val routeObject: T,
    val routeString: String,
    val icon: ImageVector
)

val topLevelRoutes = listOf(
    TopLevelRoute("Manuales", HomeSection, routeMap[HomeSection]!!, Icons.Default.Home),
    TopLevelRoute("Dados", DiceSection, routeMap[DiceSection]!!, Icons.Default.Add),
    TopLevelRoute("Personajes", CharacterTabSection, routeMap[CharacterTabSection]!!, Icons.Default.AccountBox)
)

@SuppressLint("ViewModelConstructorInComposable")
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

    val currentRoute = topLevelRoutes.find { topRoute ->
        currentDestination?.hierarchy?.any { it.route == topRoute.routeString } == true
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
                CustomModalDrawer(auth)
                Spacer(modifier = Modifier.weight(1f))
                CustomDrawerSignOutSection(onSignOutClick)
            }
        },
        content = {
            Scaffold(
                topBar = {
                    CustomTopBar(
                        text = currentRoute?.name ?: "",
                        onClick = { coroutineScope.launch { drawerState.open() } }
                    )
                },
                bottomBar = {
                    BottomNavigation(backgroundColor = DarkBrown, elevation = 8.dp) {
                        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
                        topLevelRoutes.forEach { topLevelRoute ->
                            CustomBottomNavigationItem(
                                route = topLevelRoute,
                                isSelected = currentDestination?.hierarchy?.any {
                                    it.route == topLevelRoute.routeString
                                } == true,
                                onClick = {
                                    navController.navigate(topLevelRoute.routeString) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController,
                    startDestination = routeMap[HomeSection]!!,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(routeMap[HomeSection]!!) {
                        val repository = FirebaseManualRepository()
                        val manualViewModel = ManualViewModel(repository)
                        HomeView(
                            viewModel = manualViewModel,
                            onMoreInfoClick = { manual ->
                                val gson = Gson()
                                val manualJson = gson.toJson(manual)
                                val encodedManualJson =
                                    java.net.URLEncoder.encode(manualJson, "UTF-8")
                                navController.navigate("manualDetail/$encodedManualJson")
                            },
                            navController = navController
                        )
                    }

                    composable(
                        route = "manualDetail/{manualJson}",
                        arguments = listOf(navArgument("manualJson") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val json = backStackEntry.arguments?.getString("manualJson") ?: ""
                        val decodedJson = java.net.URLDecoder.decode(json, "UTF-8")
                        val manual = Gson().fromJson(decodedJson, Manual::class.java)

                        val cleanedPoster = manual.poster
                            .replace("\\u003d", "=")
                            .replace("\\u0026", "&")
                        val cleanedManual = manual.copy(poster = cleanedPoster)

                        val repository = FirebaseManualRepository()
                        val manualViewModel = ManualViewModel(repository)
                        manualViewModel.selectManual(cleanedManual)

                        ManualDetailSection(viewModel = manualViewModel)
                    }

                    composable(routeMap[DiceSection]!!) { DiceView() }
                    composable(routeMap[CharacterTabSection]!!) { CharacterTabView() }
                }
            }
        }
    )
}
