package org.mireiavh.finalproject

import android.util.Log
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
    isUserLoggedIn: Boolean,
    onGoogleLoginClick: () -> Unit,
    onSignOutClick: () -> Unit,
    auth : AuthManager
) {
    val startDestination = if (isUserLoggedIn) {
        Log.i("LOG_IN", "user " + (auth.getCurrentUser()?.email ?: "no user logged in"))
        "home"
    } else {
        "auth"
    }

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
                navigateToHome = { navHostController.navigate("home") },
                auth = FirebaseAuth.getInstance()
            )
        }
        composable("signUp") {
            SignUpView(
                navigateToInitial = { navHostController.navigate("auth") },
                navigateToHome = { navHostController.navigate("home") },
                auth = FirebaseAuth.getInstance()
            )
        }
        composable("home") {
            HomeView()
        }
    }
}