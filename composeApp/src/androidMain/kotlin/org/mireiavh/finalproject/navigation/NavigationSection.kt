package org.mireiavh.finalproject.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import org.mireiavh.finalproject.R
import org.mireiavh.finalproject.presentation.CharacterTabView
import org.mireiavh.finalproject.presentation.DiceView
import org.mireiavh.finalproject.presentation.HomeView
import org.mireiavh.finalproject.utils.BorderedImageBox
import org.mireiavh.finalproject.utils.CustomBottomNavigationItem
import org.mireiavh.finalproject.utils.CustomButton
import org.mireiavh.finalproject.utils.CustomDivider
import org.mireiavh.finalproject.utils.CustomTitleText
import org.mireiavh.finalproject.utils.CustomTopBar
import org.mireiavh.finalproject.utils.DarkBrown
import org.mireiavh.finalproject.utils.White

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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(id = R.string.user_profile_text), color = White)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    BorderedImageBox(R.drawable.logo_image_rc, null)
                    Spacer(modifier = Modifier.height(20.dp))
                    auth.getCurrentUser()?.email?.let { CustomTitleText(it.toString(), textAlign = TextAlign.Center) }
                    Spacer(modifier = Modifier.height(20.dp))
                    CustomDivider()
                }
                Spacer(modifier = Modifier.height(30.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(id = R.string.configuration_text), color = Color.LightGray)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(stringResource(id = R.string.change_lenguaje_text), color = White)
                }

                Spacer(modifier = Modifier.height(40.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(id = R.string.suport_text), color = Color.LightGray)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(stringResource(id = R.string.suport_text), color = White)
                    Spacer(modifier = Modifier.height(20.dp))
                    CustomDivider()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(stringResource(id = R.string.suport_info_text), color = White)
                    Spacer(modifier = Modifier.height(20.dp))
                    CustomDivider()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(stringResource(id = R.string.customer_prefs_text), color = White)
                    Spacer(modifier = Modifier.height(20.dp))
                    CustomDivider()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(stringResource(id = R.string.privacy_politics_text), color = White)
                    Spacer(modifier = Modifier.height(20.dp))
                    CustomDivider()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(stringResource(id = R.string.use_terms_text), color = White)
                }

                Spacer(modifier = Modifier.height(40.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(id = R.string.devs_config_text), color = Color.LightGray)
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(stringResource(id = R.string.app_version_text), color = White)
                        Text("LST 1.0.0", color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                CustomButton(
                    onClick = { onSignOutClick() }, null, Color(0xFF4D0000),
                    stringResource(id = R.string.sign_out_text),
                    Color.Transparent
                )
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
                    BottomNavigation(
                        backgroundColor = Color(0xFF800000),
                        elevation = 8.dp
                    ) {
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
                            )
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController,
                    startDestination = HomeSection,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable<HomeSection> { HomeView() }
                    composable<DiceSection> { DiceView() }
                    composable<CharacterTabSection> { CharacterTabView() }
                }
            }

        }
    )
}

