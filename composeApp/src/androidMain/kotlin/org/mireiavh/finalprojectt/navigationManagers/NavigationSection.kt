package org.mireiavh.finalprojectt.navigationManagers

import CreateCharacterSection
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.infrastructure.repositories.FirebaseCharacterRepository
import org.mireiavh.finalprojectt.infrastructure.repositories.FirebaseDiceRepository
import org.mireiavh.finalprojectt.infrastructure.repositories.FirebaseGlobalCharacterRepository
import org.mireiavh.finalprojectt.infrastructure.repositories.FirebaseManualRepository
import org.mireiavh.finalprojectt.infrastructure.repositories.FirebaseUniverseRepository
import org.mireiavh.finalprojectt.infrastructure.viewmodels.*
import org.mireiavh.finalprojectt.ui.*
import org.mireiavh.finalprojectt.ui.characterSectionUI.CharacterSelectionSection
import org.mireiavh.finalprojectt.ui.characterSectionUI.GlobalCharacterDetailSection
import org.mireiavh.finalprojectt.ui.homeSectionUI.ManualDetailDrawerView
import org.mireiavh.finalprojectt.ui.homeSectionUI.UniverseDetailSection
import org.mireiavh.finalprojectt.utils.customs.*
import org.mireiavh.finalprojectt.utils.DarkBrown

@Serializable object HomeSection
@Serializable object DiceSection
@Serializable object CharacterTabSection
@Serializable object DiceChatSection

val routeMap = mapOf(
    HomeSection to "home",
    DiceSection to "dice",
    CharacterTabSection to "character_tab",
    DiceChatSection to "dice_chat"
)

data class TopLevelRoute<T : Any>(
    val name: String,
    val routeObject: T,
    val routeString: String,
    val iconResId: Int
)

val topLevelRoutes = listOf(
    TopLevelRoute("Manuales", HomeSection, routeMap[HomeSection]!!, R.drawable.manual_gray),
    TopLevelRoute("Dados", DiceSection, routeMap[DiceSection]!!, R.drawable.dice_gray),
    TopLevelRoute("Personajes", CharacterTabSection, routeMap[CharacterTabSection]!!, R.drawable.pj_gray),
    TopLevelRoute("Salas", DiceChatSection, routeMap[DiceChatSection]!!, R.drawable.users_gray)
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

    val manualRepository = FirebaseManualRepository()
    val manualViewModel: ManualViewModel = viewModel(
        factory = ManualViewModelFactory(manualRepository)
    )

    val universeRepository = FirebaseUniverseRepository()
    val universeViewModel: UniverseViewModel = viewModel(
        factory = UniverseViewModelFactory(universeRepository)
    )

    val globalCharacterRepository = FirebaseGlobalCharacterRepository()
    val globalCharacterViewModel: GlobalCharacterViewModel = viewModel(
        factory = GlobalCharacterViewModelFactory(globalCharacterRepository)
    )

    val diceRollViewModel = viewModel<DiceRollViewModel>(
        factory = DiceRollViewModelFactory(FirebaseDiceRepository())
    )

    val characterViewModel = viewModel<CharacterViewModel>(
        factory = CharacterViewModelFactory(FirebaseCharacterRepository())
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentRoute = topLevelRoutes.find { topRoute ->
        currentDestination?.hierarchy?.any { it.route == topRoute.routeString } == true
    }

    val currentUser = auth.getCurrentUser()
    val userId = auth.getCurrentUser()?.uid ?: ""
    val userName = currentUser?.displayName ?: currentUser?.email ?: "Usuario"
    val manualId = "default_manual_id"

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
                        text = "Hola, $userName",
                        onClick = { coroutineScope.launch { drawerState.open() } }
                    )
                },
                bottomBar = {
                    BottomNavigation(backgroundColor = DarkBrown, elevation = 8.dp) {
                        val currentDest = navController.currentBackStackEntryAsState().value?.destination
                        topLevelRoutes.forEach { topLevelRoute ->
                            CustomBottomNavigationItem(
                                route = topLevelRoute,
                                isSelected = currentDest?.hierarchy?.any {
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
                    navController = navController,
                    startDestination = routeMap[HomeSection]!!,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(routeMap[HomeSection]!!) {
                        HomeView(
                            manualViewModel = manualViewModel,
                            characterHomeViewModel = globalCharacterViewModel,
                            universeViewModel = universeViewModel,
                            onMoreInfoClick = { manual ->
                                manualViewModel.selectManual(manual)
                                navController.navigate("manualDetail")
                            },
                            onMoreInfoUniverseClick = { universe ->
                                universeViewModel.selectUniverse(universe)
                                navController.navigate("universeDetail")
                            },
                            onMoreInfoCharacterClick = { character ->
                                globalCharacterViewModel.selectCharacter(character)
                                navController.navigate("globalCharacterSelection")
                            },
                            navController = navController
                        )
                    }

                    composable("manualDetail") {
                        ManualDetailDrawerView(viewModel = manualViewModel)
                    }

                    composable("universeDetail") {
                        UniverseDetailSection(viewModel = universeViewModel)
                    }

                    composable(routeMap[DiceSection]!!) {
                        DiceView(
                            viewModel = diceRollViewModel,
                            userId = userId,
                            manualId = manualId
                        )
                    }

                    composable(routeMap[CharacterTabSection]!!) {
                        CharacterTabView(
                            viewModel = characterViewModel,
                            navController = navController,
                            onNavigateToCreate = {
                                navController.navigate("characterCreation")
                            }
                        )
                    }

                    composable("characterCreation") {
                        CreateCharacterSection(
                            navController = navController,
                            viewModel = characterViewModel,
                            onCharacterSaved = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable("globalCharacterSelection") {
                        GlobalCharacterDetailSection(
                            viewModel = globalCharacterViewModel
                        )
                    }

                    composable(routeMap[DiceChatSection]!!) {
                        ChatView()
                    }

                    composable("globalCharacterSelection") {
                        CharacterSelectionSection(
                            globalViewModel = globalCharacterViewModel,
                            userViewModel = characterViewModel,
                            userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                            onCharacterSelected = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    )
}
