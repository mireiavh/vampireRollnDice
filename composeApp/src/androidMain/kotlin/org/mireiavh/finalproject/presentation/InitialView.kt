package org.mireiavh.finalproject.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import org.mireiavh.finalproject.R
import org.mireiavh.finalproject.utils.CustomButton
import org.mireiavh.finalproject.utils.CustomImage
import org.mireiavh.finalproject.utils.CustomTitleText

@Composable
fun InitialView (navigateToLogin:() -> Unit, navigateToSignUp: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomImage(painterResource(id = R.drawable.initial_cover_vampire), ContentScale.Crop, Modifier.fillMaxSize())
        Box(modifier = Modifier.fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color(0xFF800000).copy(alpha = 0.7f)), startY = 300f, endY = 1800f))
        )
        Box(modifier = Modifier.width(265.dp).padding(vertical = 16.dp)
            .background(color = Color(0xFF4D0000).copy(alpha = 0.5f)).padding(16.dp)) {
            Text(stringResource(id = R.string.help_inital_text), color = Color.White.copy(alpha = 0.7f))
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black), startY = 700f, endY = 2000f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1.2f))
            CustomImage(painterResource(id = R.drawable.logo_vampire), ContentScale.Fit, Modifier.size(width = 250.dp, height = 80.dp))
            CustomTitleText(stringResource(id = R.string.title_initial_text), TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))
            CustomButton(Modifier.clickable { navigateToLogin() }, null, null,stringResource(id = R.string.button_inital_login), Color(0xFF4D0000))
            Spacer(modifier = Modifier.height(4.dp))
            CustomButton(Modifier.clickable {  }, painterResource(id = R.drawable.logo_google), Color(0xFF4D0000),stringResource(id = R.string.button_inital_google_login), Color.Transparent)
            Text(stringResource(id = R.string.button_inital_login_free), color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(15.dp).clickable{ navigateToSignUp() })
            Spacer(modifier = Modifier.weight(.1f))
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(1.dp).background(Color(0xFF4D0000)))
            Text(stringResource(id = R.string.politics_text), fontSize = 12.sp, textAlign = TextAlign.Center, color = Color.White, modifier = Modifier.padding(horizontal = 50.dp, vertical = 20.dp))

        }

    }
}




