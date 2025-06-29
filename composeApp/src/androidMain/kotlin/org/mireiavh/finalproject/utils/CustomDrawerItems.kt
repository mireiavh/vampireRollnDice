package org.mireiavh.finalproject.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.mireiavh.finalproject.AuthManager
import org.mireiavh.finalproject.R

@Composable
fun CustomDrawerUserTextProfile(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(stringResource(id = R.string.user_profile_text), color = White)
    }
}

@Composable
fun CustomDrawerUserNameNImage(auth: AuthManager){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        BorderedImageBox(R.drawable.logo_image_rc, null)
        Spacer(modifier = Modifier.height(20.dp))
        auth.getCurrentUser()?.email?.let { CustomTitleText(it.toString(), textAlign = TextAlign.Center) }
        Spacer(modifier = Modifier.height(20.dp))
        CustomDivider()
    }
}

@Composable
fun CustomDrawerConfigurationSection(){
    Spacer(modifier = Modifier.height(30.dp))

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(id = R.string.configuration_text), color = Color.LightGray)
        Spacer(modifier = Modifier.height(20.dp))
        Text(stringResource(id = R.string.change_lenguaje_text), color = White)
    }
}

@Composable
fun CustomDrawerSuportSection(){
    Spacer(modifier = Modifier.height(40.dp))

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(id = R.string.suport_text), color = Color.LightGray)
        Spacer(modifier = Modifier.height(20.dp))
        Text(stringResource(id = R.string.suport_text), color = White)
        Spacer(modifier = Modifier.height(20.dp))
        CustomDivider()
        Spacer(modifier = Modifier.height(20.dp))
        Text(stringResource(id = R.string.suport_info_text), color = White)
        Spacer(modifier = Modifier.height(20.dp))
        CustomDivider()
        Spacer(modifier = Modifier.height(20.dp))
        Text(stringResource(id = R.string.customer_prefs_text), color = White)
        Spacer(modifier = Modifier.height(20.dp))
        CustomDivider()
        Spacer(modifier = Modifier.height(20.dp))
        Text(stringResource(id = R.string.privacy_politics_text), color = White)
        Spacer(modifier = Modifier.height(20.dp))
        CustomDivider()
        Spacer(modifier = Modifier.height(20.dp))
        Text(stringResource(id = R.string.use_terms_text), color = White)
    }
}

@Composable
fun CustomDrawerDevsConfigSection(){
    Spacer(modifier = Modifier.height(40.dp))

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(id = R.string.devs_config_text), color = Color.LightGray)
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(id = R.string.app_version_text), color = White)
            /*
                PENDIENTE DE REVISION --> Text(stringResource(id = R.string.num_app_version_text), color = Color.Gray)
            */
            Text(stringResource(id = R.string.num_app_version_text), color = Color.Gray)
        }
    }
}

@Composable
fun CustomDrawerSignOutSection(onSignOutClick: () -> Unit){
    CustomButton(
        onClick = {onSignOutClick()}, null, Color(0xFF4D0000),
        stringResource(id = R.string.sign_out_text),
        Color.Transparent
    )
}