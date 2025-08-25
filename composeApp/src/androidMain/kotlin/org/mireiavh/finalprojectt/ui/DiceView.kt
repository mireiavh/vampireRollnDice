package org.mireiavh.finalprojectt.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.domain.model.DiceRoll
import org.mireiavh.finalprojectt.infrastructure.viewmodels.DiceRollViewModel
import org.mireiavh.finalprojectt.utils.DarkBrown
import org.mireiavh.finalprojectt.utils.White
import org.mireiavh.finalprojectt.utils.customs.*

@Composable
fun DiceView(
    viewModel: DiceRollViewModel,
    userId: String,
    manualId: String
) {
    val context = LocalContext.current
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

    var normalCount by remember { mutableStateOf("5") }
    var angerCount by remember { mutableStateOf("1") }
    var customTitle by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val allRolls by viewModel.allRolls.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var showLegendDialog by remember { mutableStateOf(false) }
    var showConfirmDeleteAll by remember { mutableStateOf(false) }

    val filteredRolls = allRolls.filter {
        it.title?.contains(searchQuery, ignoreCase = true) ?: false
    }

    val animatedRolls = remember { mutableStateListOf<DiceRoll>() }

    LaunchedEffect(currentUserId) {
        if (currentUserId.isNotEmpty()) {
            viewModel.loadRolls(currentUserId)
        }
    }

    LaunchedEffect(filteredRolls) {
        animatedRolls.clear()
        animatedRolls.addAll(filteredRolls)
    }

    LaunchedEffect(allRolls.size) {
        if (allRolls.isNotEmpty()) {
            delay(150)
            listState.animateScrollToItem(allRolls.lastIndex)
        }
    }

    suspend fun animateDeleteRolls() {
        for (i in animatedRolls.size - 1 downTo 0) {
            val delayTime = (300f * (1 - i.toFloat() / animatedRolls.size)).toLong().coerceAtLeast(50L)
            animatedRolls.removeAt(i)
            delay(delayTime)
        }
        viewModel.deleteAllRoll(currentUserId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cccccc),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.6f },
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
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Listado de tiradas de rol", color = White)

                Spacer(modifier = Modifier.weight(1f))

                CustomDetailButton(
                    onClick = { showConfirmDeleteAll = true },
                    iconResId = R.drawable.bin_peach
                )

                Spacer(modifier = Modifier.padding(4.dp))

                CustomDetailButton(
                    onClick = { showLegendDialog = true },
                    iconResId = R.drawable.info_icon
                )
            }

            LegendDialog(show = showLegendDialog, onDismiss = { showLegendDialog = false })

            ConfirmDeleteAllDialog(show = showConfirmDeleteAll, onDismiss = { showConfirmDeleteAll = false },
                onConfirm = {
                    showConfirmDeleteAll = false
                    scope.launch { animateDeleteRolls() }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            SearchBasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                labelText = stringResource(id = R.string.search_bar_text_es)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(), state = listState
            ) {
                items(animatedRolls, key = { it.id ?: it.hashCode() }) { roll ->
                    var visible by remember { mutableStateOf(true) }

                    if (roll.title == "Rouse Check") {
                        RouseCheckCard(roll = roll,
                            onDelete = { visible = false
                                scope.launch { delay(300)
                                    viewModel.deleteRoll(roll) } })
                    } else {
                        DiceRollCard(roll,
                            onReroll = { selectedIndices, originalRoll ->
                                val rerollTitle = "${originalRoll.title ?: "Dice Roll"} (Relanzamiento)"
                                viewModel.rerollSelectedDice(selectedIndices, originalRoll.copy(title = rerollTitle))
                            },
                            onDelete = { visible = false
                                scope.launch { delay(300)
                                    viewModel.deleteRoll(roll)
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val validCh = stringResource(id = R.string.valid_ch_text_es)
            CustomDiceInput(
                value = customTitle,
                onValueChange = { newValue ->
                    val isValid = newValue.isBlank() || newValue.any { it.isLetter() }
                    if (isValid) {
                        customTitle = newValue
                    } else {
                        Toast.makeText(context, validCh, Toast.LENGTH_SHORT).show()
                    }
                },
                stringResource(id = R.string.title_dice_placholder_text_es),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()
            ) {

                val validNumber = stringResource(id = R.string.valid_number_text_es)
                CustomDiceInput(
                    value = normalCount,
                    onValueChange = { newValue ->
                        if (isValidDiceInput(newValue)) {
                            if (isInRange(newValue)) {
                                normalCount = newValue
                            } else {
                                Toast.makeText(context, validNumber, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    stringResource(id = R.string.dicepool_placeholder_text_es),
                    modifier = Modifier.weight(1f)
                )

                CustomDiceInput(
                    value = angerCount,
                    onValueChange = { newValue ->
                        if (isValidDiceInput(newValue)) {
                            if (isInRange(newValue)) {
                                angerCount = newValue
                            } else {
                                Toast.makeText(context, validNumber, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    stringResource(id = R.string.fome_placholder_text_es),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()
            ) {

                CustomDetailButton(
                    onClick = {
                        onRollButtonClick(
                            context = context,
                            customTitle = customTitle,
                            manualId = manualId,
                            normalCount = normalCount,
                            angerCount = angerCount,
                            currentUserId = currentUserId,
                            onClearTitle = { customTitle = "" },
                            viewModel = viewModel
                        )
                    },
                    text = stringResource(id = R.string.roll_text_es),
                    modifier = Modifier.weight(1f)
                )

                CustomDetailButton(
                    onClick = {
                        onRouseCheckClick(
                            context = context,
                            customTitle = customTitle,
                            manualId = manualId,
                            currentUserId = currentUserId,
                            onClearTitle = { customTitle = "" },
                            viewModel = viewModel
                        )
                    },
                    text = stringResource(id = R.string.rouse_check_text_es),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// Funciones auxiliares
fun isValidDiceInput(input: String): Boolean { val numberRegex = Regex("^\\d{0,2}$")
    return input.matches(numberRegex)
}

fun isInRange(input: String, max: Int = 20): Boolean { val parsed = input.toIntOrNull()
    return parsed == null || parsed <= max
}

fun onRollButtonClick(context: android.content.Context, customTitle: String, manualId: String,
                      normalCount: String, angerCount: String, currentUserId: String, onClearTitle: () -> Unit, viewModel: DiceRollViewModel
) {
    val normal = normalCount.toIntOrNull()
    val anger = angerCount.toIntOrNull()
    if (normal == null || normal !in 0..20) {
        Toast.makeText(context, "Dice Pool debe ser un número entre 0 y 20", Toast.LENGTH_SHORT).show()
        return
    }
    if (anger == null || anger !in 0..20) {
        Toast.makeText(context, "Fome debe ser un número entre 0 y 20", Toast.LENGTH_SHORT).show()
        return
    }
    val title = if (customTitle.isNotBlank()) customTitle else "Dice Roll"
    viewModel.rollDice(title, manualId, normal, anger, currentUserId)
    viewModel.saveRoll(currentUserId)
    onClearTitle()
}

fun onRouseCheckClick(context: android.content.Context, customTitle: String, manualId: String,
                      currentUserId: String, onClearTitle: () -> Unit, viewModel: DiceRollViewModel
) {
    if (currentUserId.isBlank()) {
        Toast.makeText(context, "Usuario no válido", Toast.LENGTH_SHORT).show()
        return
    }
    val title = if (customTitle.isNotBlank()) customTitle else "Rouse Check"
    viewModel.rollDice(title, manualId, 0, 1, currentUserId)
    viewModel.saveRoll(currentUserId)
    onClearTitle()
}
