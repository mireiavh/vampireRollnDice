package org.mireiavh.finalprojectt.utils.customs

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mireiavh.finalprojectt.R
import org.mireiavh.finalprojectt.navigationManagers.TopLevelRoute
import org.mireiavh.finalprojectt.ui.CharacterSortOption
import org.mireiavh.finalprojectt.utils.BrightRed
import org.mireiavh.finalprojectt.utils.Crimson
import org.mireiavh.finalprojectt.utils.DarkBrown
import org.mireiavh.finalprojectt.utils.DeepRed
import org.mireiavh.finalprojectt.utils.LightBrown
import org.mireiavh.finalprojectt.utils.Peach


@Composable
fun CustomTitleText(title: String, textAlign: TextAlign?){
    Text(
        title,
        color = White,
        fontSize = 18.sp,
        textAlign = textAlign,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 40.dp)
    )
}

@Composable
fun CustomTitleText2(title: String, textAlign: TextAlign?){
    Text(
        title,
        color = White,
        fontSize = 18.sp,
        textAlign = textAlign,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CustomDirectionalText(text: String, onClick: () -> Unit){
    Text(
        text,
        color = White,
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
        color = White,
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
fun CustomButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF6200EE),
    contentColor: Color = White
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Text(text = title)
    }
}

@Composable
fun CustomDetailButton(
    onClick: () -> Unit,
    text: String? = null,
    modifier: Modifier = Modifier,
    iconResId: Int? = null
) {
    Button(
        onClick = onClick,
        modifier = if (text != null) modifier.fillMaxWidth() else modifier.wrapContentWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4D0000)
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        if (iconResId != null) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = if (text != null) 2.dp else 0.dp)
            )
        }
        if (text != null) {
            Text(
                text = text,
                color = White
            )
        }
    }
}


@Composable
fun ValidatedTitleTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    val message = stringResource(id = R.string.rouse_check_text_es)

    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            val isValid = newValue.isBlank() || newValue.any { it.isLetter() }
            if (isValid) {
                onValueChange(newValue)
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        },
        singleLine = true,
        textStyle = TextStyle(
            color = White,
            fontSize = 16.sp
        ),
        cursorBrush = SolidColor(Peach.copy(alpha = 0.7f)),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CustomDiceInput(value: String, onValueChange: (String) -> Unit, title: String, modifier: Modifier = Modifier) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .height(55.dp)
            .border(width = 1.5.dp, Color(0xFF800000), shape = RoundedCornerShape(2.dp)),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFF800000).copy(alpha = 0.3f),
            focusedContainerColor = Color(0xFF800000).copy(alpha = 0.5f)
        ),
        textStyle = TextStyle(
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        placeholder = {
            Text(
                text = title,
                color = Peach.copy(alpha = 0.7f),
                fontSize = 16.sp
            )
        }
    )
}

@Composable
fun CustomDialogTextButtom(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {

    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Peach.copy(alpha = 0.2f),
            contentColor = org.mireiavh.finalprojectt.utils.White.copy(alpha = 0.8f)
        ),
        shape = RoundedCornerShape(2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text)
    }
}

@Composable
fun CustomIcon(painter: Painter){
    Icon(
        painter = painter,
        contentDescription = "",
        tint = White,
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
            color = White.copy(alpha = 0.7f)
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
fun CustomTopBar(text: String, onClick: () -> Unit) {
    TopAppBar(
        backgroundColor = DarkBrown,
        title = {
            Text(text, fontWeight = FontWeight.Bold, color = White, fontSize = 18.sp ) },
        navigationIcon = {
            IconButton(onClick = {
                Log.d("CustomTopBar", "Image clicked")
                onClick()
            }) {
                Image(
                    painter = painterResource(id = R.drawable.min_logo_vampire),
                    contentDescription = "Menu",
                    modifier = Modifier
                        .size(30.dp)
                        .border(
                            width = 1.dp,
                            color = Crimson,
                            shape = RoundedCornerShape(2.dp)
                        )
                        .clip(RoundedCornerShape(2.dp))
                )
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
    val contentColor = if (isSelected) BrightRed else Peach

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = route.iconResId),
            contentDescription = route.name,
            colorFilter = ColorFilter.tint(contentColor),
            modifier = Modifier.size(24.dp)
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

@Composable
fun CustomSortDropdown(
    selectedOption: CharacterSortOption,
    onOptionSelected: (CharacterSortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var buttonWidth by remember { mutableStateOf(0) }

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBrown.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    buttonWidth = coordinates.size.width
                }
        ) {
            Text(
                selectedOption.label,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { buttonWidth.toDp() })
                .background(
                    color = LightBrown,
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            CharacterSortOption.values().forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option.label,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun SearchBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String = stringResource(id = R.string.search_bar_text_es),
    labelColor: Color = Peach.copy(alpha = 0.7.toFloat()),
    textColor: Color = White,
    backgroundColor: Color = DeepRed,
    borderColor: Color = Peach,
    shape: RoundedCornerShape = RoundedCornerShape(2.dp),
    heightDp: Int = 50,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(heightDp.dp)
            .background(backgroundColor, shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = labelColor,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(
                        text = labelText,
                        color = labelColor,
                        fontSize = 16.sp
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = TextStyle(
                        color = textColor,
                        fontSize = 16.sp
                    ),
                    cursorBrush = SolidColor(labelColor),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun <T> EnumDropdown(
    label: String,
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedLabel = selectedOption.toString().replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() }

    Column {
        OutlinedTextField(
            value = selectedLabel,
            onValueChange = {},
            readOnly = true,
            label = { androidx.compose.material.Text(label) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    androidx.compose.material.Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
        )
        androidx.compose.material.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                val optionLabel = option.toString().replace('_', ' ').lowercase()
                    .replaceFirstChar { it.uppercase() }
                androidx.compose.material.DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }) {
                    androidx.compose.material.Text(optionLabel)
                }
            }
            androidx.compose.material.DropdownMenuItem(onClick = {
                onOptionSelected(options.first())
                expanded = false
            }) {
                androidx.compose.material.Text("Ninguno")
            }
        }
    }
}
