package org.mireiavh.finalprojectt.navigationManagers

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.mireiavh.finalprojectt.infrastructure.repositories.FirebaseCharacterRepository
import org.mireiavh.finalprojectt.ui.InitialView
import org.mireiavh.finalprojectt.ui.LoginView
import org.mireiavh.finalprojectt.ui.SignUpView
import org.mireiavh.finalprojectt.infrastructure.repositories.FirebaseManualRepository
import org.mireiavh.finalprojectt.infrastructure.viewmodels.CharacterViewModel
import org.mireiavh.finalprojectt.infrastructure.viewmodels.CharacterViewModelFactory
import org.mireiavh.finalprojectt.infrastructure.viewmodels.ManualViewModel
import org.mireiavh.finalprojectt.infrastructure.viewmodels.ManualViewModelFactory

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    isUserLoggedIn: Boolean,
    onGoogleLoginClick: () -> Unit,
    onSignOutClick: () -> Unit,
    authManager: AuthManager
) {
    val startDestination = if (isUserLoggedIn) "appContent" else "auth"

    val firebaseManualRepository = FirebaseManualRepository()
    val firebaseCharacterRepository = FirebaseCharacterRepository()

    val manualFactory = ManualViewModelFactory(firebaseManualRepository)
    val characterFactory = CharacterViewModelFactory(firebaseCharacterRepository)

    val manualViewModel: ManualViewModel = viewModel(factory = manualFactory)
    val characterViewModel: CharacterViewModel = viewModel(factory = characterFactory)

    NavHost(navController = navHostController, startDestination = startDestination) {

        composable("auth") {
            InitialView(
                navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignUp = { navHostController.navigate("signUp") },
                onGoogleLoginClick = onGoogleLoginClick
            )
        }

        composable("logIn") {
            LoginView(
                navigateToInitial = { navHostController.navigate("auth") },
                navigateToHome = {
                    navHostController.navigate("appContent") {
                        popUpTo("auth") { inclusive = true }
                    }
                },
                auth = authManager
            )
        }

        composable("signUp") {
            SignUpView(
                navigateToInitial = { navHostController.navigate("auth") },
                navigateToHome = {
                    navHostController.navigate("appContent") {
                        popUpTo("auth") { inclusive = true }
                    }
                },
                auth = authManager
            )
        }

        composable("appContent") {
            menuNavigation(
                onSignOutClick = onSignOutClick,
                auth = authManager
            )
        }


    }
}