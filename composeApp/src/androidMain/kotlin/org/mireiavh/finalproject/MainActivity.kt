package org.mireiavh.finalproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
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

    private val currentUser = mutableStateOf(Firebase.auth.currentUser)

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        authManager.handleSignInResult(
            result.data,
            this,
            onSuccess = {
                runOnUiThread {
                    currentUser.value = authManager.getCurrentUser()
                    navHostController.navigate("appContent")
                }
            },
            onFailure = { e ->
                runOnUiThread {
                    Log.e("MainActivity", "Error launching Google SignIn failed", e)
                }
            }
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        try {
            authManager = AuthManager(this)

            setContent {
                navHostController = rememberNavController()

                NavigationWrapper(
                    navHostController = navHostController,
                    isUserLoggedIn = (currentUser.value != null),
                    onGoogleLoginClick = {
                        try {
                            val signInIntent = authManager.getSignInIntent()
                            launcher.launch(signInIntent)
                            Log.i("SIGN_IN", "OK Google Event SignIn")
                        } catch (e: Exception) {
                            Log.e("SIGN_IN", "Error Google Event SignIn", e)
                        }
                    },
                    onSignOutClick = {
                        try {
                            authManager.signOut {
                                currentUser.value = null
                                navHostController.navigate("auth")
                                Log.i("SIGN_OUT", "OK Firebase/Google Event SignOut")
                            }
                        } catch (e: Exception) {
                            Log.e("SIGN_OUT", "Error during Event SignOut", e)
                        }
                    },
                    authManager = authManager
                )
            }

        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate", e)
        }
    }
}


