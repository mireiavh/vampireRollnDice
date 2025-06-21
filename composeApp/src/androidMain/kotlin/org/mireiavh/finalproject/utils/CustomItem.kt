package org.mireiavh.finalproject.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
fun CustomImage(painter: Painter, contentScale: ContentScale, modifier: Modifier){
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}

@Composable
fun CustomButton(modifier: Modifier, painter: Painter?, borderColor:  Color? = null, title: String, color: Color){
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .then(
                if (borderColor != null)
                    Modifier.border(width = 1.5.dp, color = borderColor)
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