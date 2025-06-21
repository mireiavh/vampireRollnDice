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
fun NavigationWrapper (navHostController: NavHostController, auth: FirebaseAuth){

    NavHost(navController = navHostController, startDestination = "initial"){
        composable("initial"){
            InitialView(
                navigateToLogin = {navHostController.navigate("logIn")},
                navigateToSignUp = {navHostController.navigate("signUp")}
            )
        }
        composable("logIn"){
            LoginView()
        }
        composable("signUp"){
            SignUpView(auth)
        }
        composable("Home"){
            HomeView()
        }
    }

}