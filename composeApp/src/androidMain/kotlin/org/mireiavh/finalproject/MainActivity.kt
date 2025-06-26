package org.mireiavh.finalproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var authManager: AuthManager

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        authManager.handleSignInResult(
            result.data,
            this,
            onSuccess = {
                runOnUiThread {
                    if (::navHostController.isInitialized) {
                        navHostController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }
                }
            },
            onFailure = { e ->
                runOnUiThread {
                    println("Google sign in failed: $e")
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        authManager = AuthManager(this)

        setContent {
            navHostController = rememberNavController()

            // Obtenemos usuario actual
            val currentUser = authManager.getCurrentUser()

            NavigationWrapper(
                navHostController = navHostController,
                isUserLoggedIn = (currentUser != null),
                onGoogleLoginClick = {
                    val signInIntent = authManager.getSignInIntent()
                    launcher.launch(signInIntent)
                    Log.i("LOG_IN", "user " + authManager.getCurrentUser())
                },
                onSignOutClick = {
                    authManager.signOut {
                        navHostController.navigate("auth") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                },
                auth = authManager
            )
        }
    }
}


