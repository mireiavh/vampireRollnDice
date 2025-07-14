package org.mireiavh.finalprojectt.utils

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.mireiavh.finalprojectt.AuthManager
import org.mireiavh.finalprojectt.R


sealed class DrawerItem {
    data class TextItem(val text: String, val color: Color) : DrawerItem()
    data class VersionRowItem(val leftText: String, val rightText: String) : DrawerItem()
}

@Composable
fun getDrawerItems(): List<DrawerItem> {
    return listOf(
        DrawerItem.TextItem(stringResource(id = R.string.configuration_text), Color.Gray),
        DrawerItem.TextItem(stringResource(id = R.string.change_lenguaje_text), Color.White),

        DrawerItem.TextItem(stringResource(id = R.string.suport_text), Color.Gray),
        DrawerItem.TextItem(stringResource(id = R.string.suport_text), Color.White),
        DrawerItem.TextItem(stringResource(id = R.string.suport_info_text), Color.White),
        DrawerItem.TextItem(stringResource(id = R.string.customer_prefs_text), Color.White),
        DrawerItem.TextItem(stringResource(id = R.string.privacy_politics_text), Color.White),
        DrawerItem.TextItem(stringResource(id = R.string.use_terms_text), Color.White),

        DrawerItem.TextItem(stringResource(id = R.string.devs_config_text), Color.Gray),

        DrawerItem.VersionRowItem(
            leftText = stringResource(id = R.string.app_version_text),
            rightText = stringResource(id = R.string.num_app_version_text)
        )
    )
}

@Composable
fun CustomModalDrawer(auth: AuthManager){
    CustomDrawerUserTextProfile()
    CustomDrawerUserNameNImage(auth)
    DrawerList()
}


@Composable
fun DrawerList(
    onItemClick: (index: Int, item: DrawerItem) -> Unit = { _, _ -> }
) {
    val items = getDrawerItems()
    val context = LocalContext.current

    LazyColumn {
        itemsIndexed(items) { index, item ->
            val isLast = index == items.lastIndex
            val nextIsHeader = if (!isLast && items[index + 1] is DrawerItem.TextItem) {
                (items[index + 1] as DrawerItem.TextItem).color != Color.White
            } else false

            when (item) {
                is DrawerItem.TextItem -> {
                    val isHeader = item.color != Color.White

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClick(index, item)
                                if (item.color == Color.White) {
                                    Toast.makeText(context, "Clicked: ${item.text}", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .padding(horizontal = 16.dp)
                    ) {
                        if (isHeader && index != 0) {
                            Spacer(modifier = Modifier.height(32.dp))
                        } else {
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Text(
                            text = item.text,
                            color = item.color,
                            fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal
                        )

                        if (item.color == Color.White && !nextIsHeader && !isLast) {
                            Spacer(modifier = Modifier.height(12.dp))
                            CustomFullDivider()
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                is DrawerItem.VersionRowItem -> {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClick(index, item)
                                Toast.makeText(context, "Clicked version info", Toast.LENGTH_SHORT).show()
                            }
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = item.leftText, color = Color.White)
                        Text(text = item.rightText, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun CustomDrawerUserTextProfile(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(stringResource(id = R.string.user_profile_text), color = Color.Gray)
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
        Spacer(modifier = Modifier.height(40.dp))
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




