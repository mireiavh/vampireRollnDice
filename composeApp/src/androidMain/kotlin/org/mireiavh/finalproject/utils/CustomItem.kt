package org.mireiavh.finalproject.utils

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mireiavh.finalproject.navigation.TopLevelRoute


@Composable
fun CustomTitleText(title: String, textAlign: TextAlign?){
    Text(
        title,
        color = Color.White,
        fontSize = 18.sp,
        textAlign = textAlign,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 40.dp)
    )
}

@Composable
fun CustomDirectionalText(text: String, onClick: () -> Unit){
    Text(
        text,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(15.dp).clickable{ onClick() }
    )
}

@Composable
fun CustomText(text: String, textAlign: TextAlign?, modifier: Modifier, fontWeight: FontWeight?){
    Text(
        text,
        color = White,
        textAlign = textAlign,
        modifier = modifier,
        fontWeight = fontWeight
    )
}

@Composable
fun CustomAdvertisingText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = modifier
            .padding(horizontal = 50.dp, vertical = 20.dp)
    )
}

@Composable
fun CustomImage(painter: Painter, contentScale: ContentScale, modifier: Modifier){
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}

@Composable
fun CustomInput(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .height(46.dp)
            .border(width = 1.5.dp, Color(0xFF800000), shape = RoundedCornerShape(4.dp)),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFF800000).copy(alpha = 0.3f),
            focusedContainerColor = Color(0xFF800000).copy(alpha = 0.5f)
        ),
        textStyle = TextStyle(
            color = White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    )
}


@Composable
fun CustomButton(onClick: () -> Unit, painter: Painter?, borderColor:  Color? = null, title: String, color: Color){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .then(
                if (borderColor != null)
                    Modifier.border(width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(4.dp))
                else Modifier
            ),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(4.dp)
    ) {
        if (painter != null) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(16.dp)
            )
        }
        Text(
            text = title,
            color = White
        )
    }
}

@Composable
fun CustomDetailButton(onClick: () -> Unit, text: String, modifier: Modifier){
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4D0000)
        )
    ) {
        Text(
            text = text,
            color = White
        )
    }
}

@Composable
fun CustomIcon(painter: Painter){
    Icon(
        painter = painter,
        contentDescription = "",
        tint = Color.White,
        modifier = Modifier.size(30.dp)
    )
}

@Composable
fun CustomInfoItem(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(265.dp)
            .padding(vertical = 16.dp)
            .background(color = Color(0xFF4D0000).copy(alpha = 0.5f))
            .padding(16.dp)
    ) {
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun CustomDivider(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(1.dp)
            .background(Color(0xFF4D0000))
    )
}

@Composable
fun CustomFullDivider(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFF4D0000))
    )
}

@Composable
fun CustomTopBar(text: String, onClick: () -> Unit){
    TopAppBar(
        backgroundColor = DeepRed,
        title = { androidx.compose.material.Text(text, fontWeight = FontWeight.Bold, color = Color.White) },
        navigationIcon = {
            IconButton(onClick = { onClick() }) {
                Log.d("CustomTopBar", "Icon clicked")
                androidx.compose.material.Icon(Icons.Default.AccountBox, contentDescription = "Menu", tint = Color.White)
            }
        }
    )
}

@Composable
fun CustomBottomNavigationItem(
    route: TopLevelRoute<*>,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val contentColor = if (isSelected) Color.Red else Color.White

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Icon(
            imageVector = route.icon,
            contentDescription = route.name,
            tint = contentColor
        )
        Text(
            text = route.name,
            color = contentColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun BorderedImageBox(imageRes: Int, contentDescription: String? = null) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .border(
                width = 1.dp,
                color = Crimson,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
