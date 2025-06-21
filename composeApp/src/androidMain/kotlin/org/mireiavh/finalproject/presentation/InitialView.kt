package org.mireiavh.finalproject.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mireiavh.finalproject.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.painter.Painter

@Preview
@Composable
fun InitialView () {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomImage(painterResource(id = R.drawable.initial_cover_vampire), ContentScale.Crop, Modifier.fillMaxSize())
        Box(modifier = Modifier.fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color(0xFF800000).copy(alpha = 0.7f)), startY = 300f, endY = 1800f))
        )
        Box(modifier = Modifier.width(265.dp).padding(vertical = 16.dp)
            .background(color = Color(0xFF4D0000).copy(alpha = 0.5f)).padding(16.dp)) {
            Text(text = "Obten ayuda o informacion adicional", color = Color.White.copy(alpha = 0.7f),)
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black), startY = 700f, endY = 2000f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1.2f))
            CustomImage(painterResource(id = R.drawable.logo_vampire), ContentScale.Fit, Modifier.size(width = 250.dp, height = 80.dp))
            TitleText("¡Crea personajes, manéjalos y realiza tiradas de dados!")
            Spacer(modifier = Modifier.height(20.dp))
            CustomButton(Modifier.clickable {  }, null, null,"Continua con tu cuenta correo", Color(0xFF4D0000))
            Spacer(modifier = Modifier.height(4.dp))
            CustomButton(Modifier.clickable {  }, painterResource(id = R.drawable.logo_google), Color(0xFF4D0000),"Continua con Google", Color.Transparent)
            Text("Regístrate gratis", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(15.dp))
            Spacer(modifier = Modifier.weight(.1f))
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(1.dp).background(Color(0xFF4D0000)))
            Text("Al continuar, indica que has aceptado los Terminos de uso y tenemos constancia que has leido nuestra Politica de pivacidad", fontSize = 12.sp, textAlign = TextAlign.Center, color = Color.White, modifier = Modifier.padding(horizontal = 50.dp, vertical = 20.dp))

        }

    }
}


@Composable
fun TitleText(title: String){
    Text(
        title,
        color = Color.White,
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
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
fun CustomButton(modifier:Modifier, painter: Painter?, borderColor:  Color? = null, title: String, color: Color){
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


