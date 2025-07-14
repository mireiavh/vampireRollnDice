package org.mireiavh.finalprojectt.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.infrastructure.controllers.DiceRollViewModel

@Composable
fun DiceView (
    viewModel: DiceRollViewModel,
    userId: String,
    manualId: String
    ) {
        var normalCount by remember { mutableStateOf("5") }
        var angerCount by remember { mutableStateOf("1") }

        val diceRoll by viewModel.currentRoll.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1A0E0E)) // fondo oscuro
                .padding(16.dp)
        ) {
            Text(
                text = "Dice Rolls",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Rouse Check",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            diceRoll?.angerDice?.take(1)?.let {
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    DiceIcon(it.first())
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "You're not getting hungrier.",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            diceRoll?.let { roll ->
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        roll.normalDice.forEach { DiceIcon(it) }
                        roll.angerDice.forEach { DiceIcon(it) }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Re-Roll", color = Color.White)

                    Row {
                        repeat(3) { DiceIcon("neutral") } // ejemplo
                        DiceIcon("great_success")
                        DiceIcon("success")
                        DiceIcon("skull")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Success or bestial failure: ${roll.summary.successes} success(es)",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        viewModel.rollDice(
                            title = "Rouse Check",
                            manualId = manualId,
                            normalCount = normalCount.toIntOrNull() ?: 0,
                            angerCount = angerCount.toIntOrNull() ?: 0
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Rouse Check", color = Color.White)
                }

                Button(
                    onClick = {
                        viewModel.saveRoll(userId)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Roll", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = normalCount,
                    onValueChange = { normalCount = it },
                    label = { Text("Dice Pool") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.Red,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Red,
                        focusedLabelColor = Color.Red,
                        unfocusedLabelColor = Color.White
                    )
                )

                OutlinedTextField(
                    value = angerCount,
                    onValueChange = { angerCount = it },
                    label = { Text("Fome") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.Red,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Red,
                        focusedLabelColor = Color.Red,
                        unfocusedLabelColor = Color.White
                    )
                )
            }
        }

}

@Composable
fun DiceIcon(type: String) {
    val resId = when (type) {
        "success" -> R.drawable.exito
        "great_success" -> R.drawable.critico
        "skull" -> R.drawable.fallo
        "neutral" -> R.drawable.neutro
        else -> R.drawable.neutro
    }

    Image(
        painter = painterResource(id = resId),
        contentDescription = type,
        modifier = Modifier
            .size(40.dp)
            .padding(4.dp)
    )
}