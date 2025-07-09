package org.mireiavh.finalproject.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.mireiavh.finalproject.R
import org.mireiavh.finalproject.utils.CustomAdvertisingText
import org.mireiavh.finalproject.utils.CustomButton
import org.mireiavh.finalproject.utils.CustomDirectionalText
import org.mireiavh.finalproject.utils.CustomDivider
import org.mireiavh.finalproject.utils.CustomImage
import org.mireiavh.finalproject.utils.CustomInfoItem
import org.mireiavh.finalproject.utils.CustomTitleText

@Composable
fun InitialView (navigateToLogin:() -> Unit, navigateToSignUp: () -> Unit, onGoogleLoginClick: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomImage(painterResource(id = R.drawable.initial_cover_vampire), ContentScale.Crop, Modifier.fillMaxSize())
        Box(modifier = Modifier.fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color(0xFF800000).copy(alpha = 0.7f)), startY = 300f, endY = 1800f))
        )
        CustomInfoItem(stringResource(id = R.string.help_inital_text))
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black), startY = 700f, endY = 2000f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1.2f))

            CustomImage(painterResource(id = R.drawable.logo_vampire), ContentScale.Fit, Modifier.size(width = 250.dp, height = 80.dp))
            CustomTitleText(stringResource(id = R.string.title_initial_text), TextAlign.Center)

            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(onClick = {navigateToLogin()}, null, null,stringResource(id = R.string.button_inital_login), Color(0xFF4D0000))

            Spacer(modifier = Modifier.height(4.dp))

            CustomButton(onClick = {onGoogleLoginClick()}, painterResource(id = R.drawable.logo_google), Color(0xFF4D0000),stringResource(id = R.string.button_inital_google_login), Color.Transparent)
            CustomDirectionalText(stringResource(id = R.string.button_inital_login_free), navigateToSignUp)
            Spacer(modifier = Modifier.weight(.1f))

            CustomDivider()
            CustomAdvertisingText(stringResource(id = R.string.politics_text))
        }

    }
}




