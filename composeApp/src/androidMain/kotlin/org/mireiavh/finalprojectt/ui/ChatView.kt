package org.mireiavh.finalprojectt.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.utils.DarkBrown
import org.mireiavh.finalprojectt.utils.White
import org.mireiavh.finalprojectt.utils.customs.CustomDetailButton
import org.mireiavh.finalprojectt.utils.customs.CustomTitleText

@Composable
fun ChatView() {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
    ) {
        Image(
            painter = painterResource(id = R.drawable.eeeeeee),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 1f },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            DarkBrown,
                            DarkBrown.copy(alpha = 0f),
                            DarkBrown
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTitleText(
                    title = "Salas para multijugador",
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text("Este apartado estará destinado a crear y unirse a salas, donde podrás conectarte con tus amigos y jugar partidas de rol en grupo.\n\nPor ahora esta funcionalidad aún no está disponible en esta versión, pero llegará en próximas actualizaciones.",
                    color = White, textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.height(40.dp))

                CustomDetailButton(
                    onClick = {
                        Toast.makeText(context, "Funcionalidad en desarrollo 🚧", Toast.LENGTH_SHORT).show()
                    },
                    text = "Próximamente",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.7f)
                )
            }
        }
    }
}