package org.mireiavh.finalprojectt.utils.customs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.domain.model.DiceRoll
import org.mireiavh.finalprojectt.utils.DeepRed
import org.mireiavh.finalprojectt.utils.Peach
import androidx.compose.material3.*
import androidx.compose.ui.res.stringResource
import org.mireiavh.finalprojectt.utils.White


@Composable
fun DiceRollCard(
    roll: DiceRoll,
    onReroll: (List<Int>, DiceRoll) -> Unit,
    onDelete: (DiceRoll) -> Unit
) {
    var selectedIndices by remember { mutableStateOf(setOf<Int>()) }
    var expanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDetailsDialog by remember { mutableStateOf(false) }

    ConfirmDeleteRollDialog(
        show = showDeleteDialog,
        onConfirm = {
            showDeleteDialog = false
            onDelete(roll)
        },
        onDismiss = { showDeleteDialog = false }
    )

    RollDetailsDialog(
        show = showDetailsDialog,
        roll = roll,
        onDismiss = { showDetailsDialog = false }
    )

    Card(
        backgroundColor = DeepRed.copy(alpha = 0.9f),
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Peach, RoundedCornerShape(2.dp)),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            RollHeaderWithDetails(
                title = roll.title ?: "Dice Roll",
                expanded = expanded,
                onExpandedChange = { expanded = it },
                onShowDetails = { showDetailsDialog = true },
                onDelete = { showDeleteDialog = true }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Column {
                    if (roll.normalDice.isNotEmpty()) {
                        Text("Dados normales:", color = Peach)
                        FlowRow(
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            roll.normalDice.forEachIndexed { index, die ->
                                val isSelected = selectedIndices.contains(index)
                                NormalDiceIcon(
                                    type = die,
                                    isSelected = isSelected,
                                    onClick = {
                                        selectedIndices = if (isSelected)
                                            selectedIndices - index
                                        else
                                            selectedIndices + index
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (roll.angerDice.isNotEmpty()) {
                        Text("Dados de hambre:", color = Peach)
                        FlowRow(
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            roll.angerDice.forEach { die ->
                                val hungerType = when(die) {
                                    "success" -> "success_fome"
                                    "great_success" -> "great_success_fome"
                                    "fail" -> "fail_fome"
                                    "critical_fail" -> "critical_fail_fome"
                                    else -> die
                                }
                                HungerDiceIcon(type = hungerType)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = stringResource(id = R.string.select_dice_text_es), color = Peach)

            if (selectedIndices.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                CustomDetailButton(
                    onClick = { onReroll(selectedIndices.toList(), roll) },
                    text = stringResource(id = R.string.reselect_dice_text_es)
                )
            }
        }
    }
}

@Composable
fun RouseCheckCard(
    roll: DiceRoll,
    onDelete: (DiceRoll) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    ConfirmDeleteRollDialog(
        show = showDeleteDialog,
        onConfirm = {
            showDeleteDialog = false
            onDelete(roll)
        },
        onDismiss = { showDeleteDialog = false }
    )

    Card(
        backgroundColor = DeepRed.copy(alpha = 0.9f),
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Peach, RoundedCornerShape(2.dp)),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            RollHeader(
                title = roll.title ?: "Roll",
                expanded = expanded,
                onExpandedChange = { expanded = it },
                onDelete = { showDeleteDialog = true }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                roll.normalDice.forEach { NormalDiceIcon(it) }
                roll.angerDice.forEach { HungerDiceIcon(it) }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Successes: ${roll.summary.successes}", color = Peach)

            Text(
                "Rouse Check: ${if (roll.summary.successes >= 1) "Success" else "Failure"}",
                color = Peach
            )
        }
    }
}

@Composable
fun NormalDiceIcon(type: String, isSelected: Boolean = false, onClick: (() -> Unit)? = null) {
    val resId = when (type) {
        "success" -> R.drawable.success_dice
        "great_success" -> R.drawable.critical_dice
        "fail" -> R.drawable.fail_dice
        "critical_fail" -> R.drawable.fail_dice
        else -> R.drawable.fail_dice
    }

    val borderColor = if (isSelected) Color.Red else Color.Transparent

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp)
            .background(Color.Transparent)
            .border(2.dp, borderColor)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = type,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun HungerDiceIcon(type: String, isSelected: Boolean = false, onClick: (() -> Unit)? = null) {
    val resId = when (type) {
        "success_fome" -> R.drawable.success_fome_dice
        "great_success_fome" -> R.drawable.critical_fome_dice
        "fail_fome" -> R.drawable.fail_fome_dice
        "critical_fail_fome" -> R.drawable.critical_fail_fome_dice
        else -> R.drawable.fail_fome_dice
    }

    val borderColor = if (isSelected) Color.Red else Color.Transparent

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp)
            .background(Color.Transparent)
            .border(2.dp, borderColor)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = type,
            modifier = Modifier.fillMaxSize()
        )
    }
}



@Composable
fun textFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
    textColor = Color.White,
    focusedBorderColor = Color.Red,
    unfocusedBorderColor = Color.White,
    cursorColor = Color.Red,
    focusedLabelColor = Color.Red,
    unfocusedLabelColor = Color.White
)

@Composable
fun LegendDialog(
    show: Boolean,
    onDismiss: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = DeepRed.copy(alpha = 0.9f),
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier.border(1.dp, Peach, RoundedCornerShape(2.dp)),
            title = {
                Text(
                    text = "Leyenda",
                    color = Color.White
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Éxito normal", color = Peach)
                    Text("Éxito crítico", color = Peach)
                    Text("Fallo crítico (Skull)", color = Peach)
                    Text("Resultado neutro", color = Peach)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Los dados rojos representan el hambre.", color = Peach)
                }
            },
            confirmButton = {},
            dismissButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    CustomDetailButton(
                        onClick = onDismiss,
                        text = stringResource(id = R.string.cancel_text_es)
                    )
                }
            }
        )
    }
}


@Composable
fun ConfirmDeleteAllDialog(
    show: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            backgroundColor = DeepRed.copy(alpha = 0.9f),
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier.border(1.dp, Peach, RoundedCornerShape(2.dp)),
            title = {
                Text(
                    text = stringResource(id = R.string.delete_confirm_text_es),
                    color = Color.White
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.delete_dice_qa_text_es),
                    color = Peach
                )
            },
            confirmButton = {
                CustomDetailButton(
                    onClick = onConfirm,
                    text = stringResource(id = R.string.confirm_text_es)
                )
            },
            dismissButton = {
                CustomDetailButton(
                    onClick = onDismiss,
                    text = stringResource(id = R.string.cancel_text_es)
                )
            }
        )
    }
}

@Composable
fun ConfirmDeleteRollDialog(
    show: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            backgroundColor = DeepRed.copy(alpha = 0.9f),
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier.border(1.dp, Peach, RoundedCornerShape(2.dp)),
            title = {
                Text(
                    text = stringResource(id = R.string.delete_confirm_text_es),
                    color = Color.White
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.delete_dice_qa_text_es),
                    color = Peach
                )
            },
            confirmButton = {
                CustomDetailButton(
                    onClick = onConfirm,
                    text = stringResource(id = R.string.confirm_text_es)
                )
            },
            dismissButton = {
                CustomDetailButton(
                    onClick = onDismiss,
                    text = stringResource(id = R.string.cancel_text_es)
                )
            }
        )
    }
}

@Composable
fun RollDetailsDialog(
    show: Boolean,
    roll: DiceRoll,
    onDismiss: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            backgroundColor = DeepRed.copy(alpha = 0.9f),
            shape = RoundedCornerShape(2.dp),
            modifier = Modifier.border(1.dp, Peach, RoundedCornerShape(2.dp)),
            title = { Text("Detalles del lanzamiento", color = Color.White) },
            text = {
                Box(
                    modifier = Modifier
                        .heightIn(min = 100.dp, max = 400.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text("Título: ${roll.title ?: "N/A"}", color = Peach)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Éxitos: ${roll.summary.successes}", color = Peach)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Dados normales:", color = Peach)
                        FlowRow(
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            roll.normalDice.forEach {
                                NormalDiceIcon(type = it, isSelected = false)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Dados de hambre:", color = Peach)
                        FlowRow(
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            roll.angerDice.forEach {
                                HungerDiceIcon(type = it, isSelected = false)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                ){ CustomDetailButton(onClick = onDismiss, text = "Cerrar") }
            }
        )
    }
}

@Composable
fun RollHeader(
    title: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val optionsEs = stringResource(id = R.string.options_text_es)
    val deleteEs = stringResource(id = R.string.delete_text_es)

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.weight(1f)
        )

        Box {
            IconButton(onClick = { onExpandedChange(true) }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.options_text_es),
                    tint = Color.White
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier.background(DeepRed.copy(alpha = 0.9f))
            ) {
                DropdownMenuItem(
                    onClick = { },
                    text = { Text(optionsEs, color = Peach) }
                )

                DropdownMenuItem(
                    onClick = {
                        onExpandedChange(false)
                        onDelete()
                    },
                    text = { Text(deleteEs, color = Color.White) }
                )
            }
        }
    }
}

@Composable
fun RollHeaderWithDetails(
    title: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onShowDetails: () -> Unit,
    onDelete: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.weight(1f)
        )

        Box {
            IconButton(onClick = { onExpandedChange(true) }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Opciones",
                    tint = Color.White
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier.background(DeepRed.copy(alpha = 0.9f))
            ) {
                DropdownMenuItem(
                    onClick = {
                        onExpandedChange(false)
                        onShowDetails()
                    },
                    text = { Text("Detalles", color = Peach) }
                )

                DropdownMenuItem(
                    onClick = {
                        onExpandedChange(false)
                        onDelete()
                    },
                    text = { Text("Borrar", color = Peach) }
                )
            }
        }
    }
}






