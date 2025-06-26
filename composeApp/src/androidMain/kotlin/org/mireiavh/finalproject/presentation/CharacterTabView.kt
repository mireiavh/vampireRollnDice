package org.mireiavh.finalproject.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.mireiavh.finalproject.navigation.CharacterTabSection

@Composable
fun CharacterTabView (){

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Vista Character")
        }
    }

}