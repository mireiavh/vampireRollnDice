package org.mireiavh.finalprojectt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.mireiavh.finalprojectt.utils.customs.LoadDataExample

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var authManager: AuthManager

    private val currentUser = mutableStateOf(Firebase.auth.currentUser)
    private val isSigningIn = mutableStateOf(false)

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        authManager.handleSignInResult(
            result.data,
            this,
            onSuccess = {
                runOnUiThread {
                    currentUser.value = authManager.getCurrentUser()
                    isSigningIn.value = false
                    navHostController.navigate("appContent")
                }
            },
            onFailure = { e ->
                runOnUiThread {
                    isSigningIn.value = false
                    Log.e("MainActivity", "Error launching Google SignIn failed", e)
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        try {
            authManager = AuthManager(this)

            setContent {
                navHostController = rememberNavController()

                MaterialTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        if (isSigningIn.value) {
                            LoadDataExample() //pendiente de revision
                        } else {
                            NavigationWrapper(
                                navHostController = navHostController,
                                isUserLoggedIn = (currentUser.value != null),
                                onGoogleLoginClick = {
                                    try {
                                        isSigningIn.value = true
                                        val signInIntent = authManager.getSignInIntent()
                                        launcher.launch(signInIntent)
                                        Log.i("SIGN_IN", "OK Google Event SignIn")
                                    } catch (e: Exception) {
                                        isSigningIn.value = false
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
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate", e)
        }
    }
}


