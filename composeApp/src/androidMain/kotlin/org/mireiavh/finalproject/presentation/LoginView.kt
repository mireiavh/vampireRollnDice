package org.mireiavh.finalproject.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import org.mireiavh.finalproject.R
import org.mireiavh.finalproject.utils.CustomAdvertisingText
import org.mireiavh.finalproject.utils.CustomDetailButton
import org.mireiavh.finalproject.utils.CustomDivider
import org.mireiavh.finalproject.utils.CustomIcon
import org.mireiavh.finalproject.utils.CustomImage
import org.mireiavh.finalproject.utils.CustomInput
import org.mireiavh.finalproject.utils.CustomText
import org.mireiavh.finalproject.utils.CustomTitleText

@Composable
fun LoginView(navigateToInitial:() -> Unit, navigateToHome:() -> Unit, auth: FirebaseAuth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomImage(painterResource(id = R.drawable.initial_cover_vampire2), ContentScale.Crop, Modifier.fillMaxSize())
        Box(modifier = Modifier.fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color(0xFF800000).copy(alpha = 0.7f)), startY = 300f, endY = 1800f))
        )

        Column(
            modifier = Modifier.fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black), startY = 700f, endY = 2000f)),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                CustomIcon(painterResource(id = R.drawable.ic_arrow_back_24))
                CustomText(stringResource(id = R.string.back_text), null, Modifier.padding(start = 4.dp).clickable{ navigateToInitial() }, FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(20.dp))

            CustomTitleText(stringResource(id = R.string.login_text), TextAlign.Center)

            Spacer(modifier = Modifier.height(20.dp))

            CustomText(stringResource(id = R.string.email_text), TextAlign.Left, Modifier.padding(horizontal = 40.dp), null)

            Spacer(modifier = Modifier.height(10.dp))

            CustomInput(value = email, onValueChange = { email = it })

            Spacer(modifier = Modifier.height(20.dp))

            CustomText(stringResource(id = R.string.password_text), TextAlign.Left, Modifier.padding(horizontal = 40.dp),null)

            Spacer(modifier = Modifier.height(10.dp))

            CustomInput(value = password, onValueChange = { password = it })

            Spacer(modifier = Modifier.height(20.dp))

            CustomDetailButton(onClick = {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.i("aris", "LOGIN OK")
                        navigateToHome()
                    }else{
                        Log.i("aris", "LOGIN noooooooo")
                    }
                }
            }, stringResource(id = R.string.register_text), Modifier.width(150.dp).align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.weight(.1f))

            CustomDivider()
            CustomAdvertisingText(stringResource(id = R.string.politics_text))
        }

    }

}