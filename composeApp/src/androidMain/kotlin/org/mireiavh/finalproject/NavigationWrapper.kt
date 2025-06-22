package org.mireiavh.finalproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import org.mireiavh.finalproject.presentation.HomeView
import org.mireiavh.finalproject.presentation.InitialView
import org.mireiavh.finalproject.presentation.LoginView
import org.mireiavh.finalproject.presentation.SignUpView
@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    onGoogleLoginClick: () -> Unit
) {
    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialView(
                navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignUp = { navHostController.navigate("signUp") },
                onGoogleLoginClick = onGoogleLoginClick
            )
        }
        composable("logIn") {
            LoginView(
                navigateToInitial = { navHostController.navigate("initial") },
                navigateToHome = { navHostController.navigate("home") },
                auth = auth
            )
        }
        composable("signUp") {
            SignUpView(
                navigateToInitial = { navHostController.navigate("initial") },
                navigateToHome = { navHostController.navigate("home") },
                auth = auth
            )
        }
        composable("home") {
            HomeView()
        }
    }
}