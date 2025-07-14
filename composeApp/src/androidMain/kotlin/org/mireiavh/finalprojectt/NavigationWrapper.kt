package org.mireiavh.finalprojectt

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import org.mireiavh.finalprojectt.ui.HomeSection.ManualDetailSection
import org.mireiavh.finalprojectt.navigation.menuNavigation
import org.mireiavh.finalprojectt.ui.InitialView
import org.mireiavh.finalprojectt.ui.LoginView
import org.mireiavh.finalprojectt.ui.SignUpView
import org.mireiavh.finalprojectt.domain.model.Manual
import org.mireiavh.finalprojectt.infrastructure.FirebaseManualRepository
import org.mireiavh.finalprojectt.infrastructure.controllers.ManualViewModel
import org.mireiavh.finalprojectt.infrastructure.controllers.ManualViewModelFactory
import java.net.URLDecoder

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    isUserLoggedIn: Boolean,
    onGoogleLoginClick: () -> Unit,
    onSignOutClick: () -> Unit,
    authManager: AuthManager
) {
    val startDestination = if (isUserLoggedIn) "appContent" else "auth"
    val gson = Gson()

    val myRepositoryInstance = FirebaseManualRepository()
    val factory = ManualViewModelFactory(myRepositoryInstance)
    val viewModel: ManualViewModel = viewModel(factory = factory)

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

        composable(
            route = "manualDetail/{manualJson}",
            arguments = listOf(navArgument("manualJson") { type = NavType.StringType })
        ) { backStackEntry ->

            val json = backStackEntry.arguments?.getString("manualJson") ?: ""
            val decodedJson = URLDecoder.decode(json, "UTF-8")
                .replace("\\u003d", "=")
                .replace("\\u0026", "&")
            val manual = gson.fromJson(decodedJson, Manual::class.java)

            viewModel.selectManual(manual)
            ManualDetailSection(viewModel = viewModel)
        }

    }
}