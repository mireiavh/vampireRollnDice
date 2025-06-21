package org.mireiavh.finalproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.mireiavh.finalproject.presentation.InitialView
import org.mireiavh.finalproject.presentation.LoginView
import org.mireiavh.finalproject.presentation.SignUpView

@Composable
fun NavigationWrapper (navHostController: NavHostController){

    NavHost(navController = navHostController, startDestination = "initial"){
        composable("initial"){
            InitialView()
        }
        composable("login"){
            LoginView()
        }
        composable("signUp"){
            SignUpView()
        }
    }

}