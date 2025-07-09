package org.mireiavh.finalproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.mireiavh.finalproject.navigation.menuNavigation
import org.mireiavh.finalproject.application.InitialView
import org.mireiavh.finalproject.application.LoginView
import org.mireiavh.finalproject.application.SignUpView

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    isUserLoggedIn: Boolean,
    onGoogleLoginClick: () -> Unit,
    onSignOutClick: () -> Unit,
    authManager: AuthManager
) {
    val startDestination = if (isUserLoggedIn) "appContent" else "auth"

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