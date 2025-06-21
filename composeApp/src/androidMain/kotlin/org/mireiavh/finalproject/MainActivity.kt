package org.mireiavh.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContent {
            navHostController = rememberNavController()
            NavigationWrapper(navHostController, auth)

        }
    }

    override fun onStart(){
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){

        }
    }
}


