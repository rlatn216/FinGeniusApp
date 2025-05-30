package com.skim.core.disignsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skim.core.disignsystem.R
import com.skim.core.disignsystem.theme.SKimTheme
import com.skim.core.disignsystem.theme.background1Color
import com.skim.core.disignsystem.theme.btnBackground1Color
import com.skim.core.disignsystem.theme.disableColor
import com.skim.core.disignsystem.theme.divider2Color
import com.skim.core.disignsystem.theme.grayColor
import com.skim.core.disignsystem.theme.grayPressedColor
import com.skim.core.disignsystem.theme.mainColor
import com.skim.core.disignsystem.theme.point1Color
import com.skim.core.disignsystem.theme.sub1Color
import com.skim.core.disignsystem.theme.sub1ColorDis
import com.skim.core.disignsystem.theme.sub2Color
import com.skim.core.disignsystem.theme.topBarBtnBackground1Color
import com.skim.core.disignsystem.theme.topBarBtnBackground2Color
import com.skim.core.disignsystem.theme.unfocusedColor

@Preview(device = Devices.AUTOMOTIVE_1024p, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewButtons() {
    SKimTheme {
        val scrollState = rememberScrollState()
        Row(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Column {
                Row {
                    Column {
                        ColorButton(text = "Text1", buttonStyle = ButtonStyle.Big) { }
                        ColorButton(
                            text = "Text2",
                            enabled = false,
                            buttonStyle = ButtonStyle.Big
                        ) { }

                        ColorButton(text = "Text3", buttonStyle = ButtonStyle.Basic) { }
                        ColorButton(
                            text = "Text4",
                            enabled = false,
                            buttonStyle = ButtonStyle.Basic
                        ) { }
                    }
                    Column {
                        GrayColorButton(
                            text = "Text5",
                            buttonStyle = ButtonStyle.Big,
                            onClick = { }
                        )
                        GrayColorButton(
                            enabled = false,
                            text = "Text6",
                            buttonStyle = ButtonStyle.Big,
                            onClick = { }
                        )

                        GrayColorButton(
                            text = "Text7",
                            buttonStyle = ButtonStyle.Basic,
                            onClick = { }
                        )
                        GrayColorButton(
                            enabled = false,
                            text = "Text8",
                            buttonStyle = ButtonStyle.Basic,
                            onClick = { }
                        )

                        GrayColorButton(
                            text = "Text9",
                            buttonStyle = ButtonStyle.Small,
                            onClick = { }
                        )
                        GrayColorButton(
                            enabled = false,
                            text = "Text10",
                            buttonStyle = ButtonStyle.Small,
                            onClick = { }
                        )
                    }
                    Column {
                        TopBarButton(
                            icon = R.drawable.tempimage,
                            pressedIcon = R.drawable.tempimage,
                            text = "Text90",
                            onClick = { }
                        )
                        TopBarButton(
                            icon = R.drawable.tempimage,
                            pressedIcon = R.drawable.tempimage,
                            text = "Text91",
                            backgroundColor = MaterialTheme.colors.topBarBtnBackground1Color,
                            onClick = { }
                        )
                        TopBarButton(
                            enabled = false,
                            icon = R.drawable.tempimage,
                            pressedIcon = R.drawable.tempimage,
                            text = "Text92",
                            onClick = { },
                        )
                    }
                }

                Row {
                    Column {
                        RoundButton(text = "Text11", onClick = { })
                        RoundButton(enabled = false, text = "Text12", onClick = { })
                        RoundButton(text = "Text13", buttonStyle = ButtonStyle.Small, onClick = { })
                        RoundButton(
                            enabled = false,
                            text = "Text14",
                            buttonStyle = ButtonStyle.Small,
                            onClick = { }
                        )
                    }

                    Column {
                        RoundImageButton(text = "Text", onClick = { })
                        RoundImageButton(enabled = false, text = "Text", onClick = { })
                    }

                    Column {
                        ChoiceButton(selected = false, text = "Choice", onClick = { })
                        ChoiceButton(selected = true, text = "Choice", onClick = { })
                    }
                }

                Row {
                    Column {
                        GrayDialogButton(
                            onClick = { },
                            enabled = true,
                            text = "DialogBtn1",
                            buttonStyle = ButtonStyle.Big
                        )

                        GrayDialogButton(
                            onClick = { },
                            enabled = false,
                            text = "DialogBtn2",
                            buttonStyle = ButtonStyle.Basic
                        )

                        ColorDialogButton(
                            onClick = { },
                            enabled = true,
                            text = "DialogBtn3",
                            buttonStyle = ButtonStyle.Big
                        )

                        ColorDialogButton(
                            onClick = { },
                            enabled = false,
                            text = "DialogBtn4",
                            buttonStyle = ButtonStyle.Basic

                        )
                    }
                }
            }

            Column {
                Row {
                    MainNewButton(text = "신청서 작성", onClick = { })

                    Column {
                        TopNavigationButton(
                            icon = R.drawable.tempimage,
                            pressedIcon = R.drawable.tempimage,
                            onClick = { }
                        )

                        TopNavigationButton(
                            enabled = false,
                            icon = R.drawable.tempimage,
                            pressedIcon = R.drawable.tempimage,
                            onClick = { }
                        )
                    }

                    Column {
                        TopNavigationButton(
                            icon = R.drawable.tempimage,
                            pressedIcon = R.drawable.tempimage,
                            onClick = { }
                        )

                        TopNavigationButton(
                            enabled = false,
                            icon = R.drawable.tempimage,
                            pressedIcon = R.drawable.tempimage,
                            onClick = { }
                        )
                    }
                }

                Row(modifier = Modifier.background(color = MaterialTheme.colors.mainColor)) {
                    ElectronicTabButton(
                        selected = false,
                        text = "적정성 보고서",
                        onClick = { }
                    )

                    ElectronicTabButton(
                        selected = true,
                        text = "상품설명서",
                        onClick = { }
                    )

                    ElectronicTabButton(
                        selected = false,
                        text = "가입신청서",
                        onClick = { }
                    )
                }

                Row {
                    MainToggleButton(
                        selected = false,
                        text = "Text",
//                        icon = R.drawable.tempimage,
//                        selectedIcon = R.drawable.tempimage,
                        onClick = { }
                    )
                    MainToggleButton(
                        selected = true,
                        icon = R.drawable.tempimage,
                        selectedIcon = R.drawable.tempimage,
                        text = "Text",
                        onClick = { }
                    )
                    MainToggleButton(
                        selected = false,
                        enabled = false,
                        icon = R.drawable.tempimage,
                        selectedIcon = R.drawable.tempimage,
                        text = "Text",
                        onClick = { }
                    )
                }

                Row {
                    Column {
                        CircleImageButton(
                            icon = R.drawable.tempimage,
                            pressedIcon = R.drawable.tempimage,
                            onClick = { }
                        )
                        CircleImageButton(
                            enabled = false,
                            icon = R.drawable.tempimage,
                            pressedIcon = R.drawable.tempimage,
                            onClick = { }
                        )
                    }

                    Column {
                        Row {
                            PageControlButton(
                                pagingButtonStyle = PagingButtonStyle.Back,
                                onClick = { })
                            PageControlButton(
                                pagingButtonStyle = PagingButtonStyle.Forward,
                                onClick = { })
                            PageControlButton(
                                pagingButtonStyle = PagingButtonStyle.Home,
                                onClick = { })
                            PageControlButton(
                                pagingButtonStyle = PagingButtonStyle.End,
                                onClick = { })
                        }
                        Row {
                            PageControlButton(
                                pagingButtonStyle = PagingButtonStyle.Back,
                                enabled = false,
                                onClick = { }
                            )
                            PageControlButton(
                                pagingButtonStyle = PagingButtonStyle.Forward,
                                enabled = false,
                                onClick = { }
                            )
                            PageControlButton(
                                pagingButtonStyle = PagingButtonStyle.Home,
                                enabled = false,
                                onClick = { }
                            )
                            PageControlButton(
                                pagingButtonStyle = PagingButtonStyle.End,
                                enabled = false,
                                onClick = { }
                            )
                        }
                    }
                }

                val isChecked = remember { mutableStateOf(false) }
                val isSelected = remember { mutableStateOf(false) }

                Row {
                    Column {
                        CheckButton(checked = true, onCheckedChange = {})
                        CheckButton(checked = isChecked.value, onCheckedChange = {
                            isChecked.value = it
                        })
                        CheckButton(checked = true, onCheckedChange = {}, enabled = false)
                        CheckButton(checked = false, onCheckedChange = {}, enabled = false)
                    }

                    Column {
                        CheckButton(checked = true, onCheckedChange = {}, text = "Text")
                        CheckButton(checked = isChecked.value, onCheckedChange = {
                            isChecked.value = it
                        }, text = "Text")
                        CheckButton(
                            checked = true,
                            onCheckedChange = {},
                            enabled = false,
                            text = "Text"
                        )
                        CheckButton(
                            checked = false,
                            onCheckedChange = {},
                            enabled = false,
                            text = "Text"
                        )
                    }

                    Column {
                        RadioButton(selected = true, onClick = {})
                        RadioButton(selected = isSelected.value, onClick = {
                            isSelected.value = !isSelected.value
                        })
                        RadioButton(selected = true, onClick = {}, enabled = false)
                        RadioButton(selected = false, onClick = {}, enabled = false)
                    }

                    Column {
                        RadioButton(selected = true, onClick = {}, text = "Text")
                        RadioButton(selected = isSelected.value, onClick = {
                            isSelected.value = !isSelected.value
                        }, text = "Text")
                        RadioButton(
                            selected = true,
                            onClick = {},
                            enabled = false,
                            text = "Text"
                        )
                        RadioButton(
                            selected = false,
                            onClick = {},
                            enabled = false,
                            text = "Text"
                        )
                    }

                    Column {
                        Row(modifier = Modifier.background(Color(0x33000000))) {
                            IconButton(
                                onClick = {},
                                icon = R.drawable.tempimage,
                                pressedIcon = R.drawable.tempimage
                            )
                            IconButton(
                                onClick = {},
                                icon = R.drawable.tempimage,
                                pressedIcon = R.drawable.tempimage
                            )
                        }

                        Row {
                            IconButton(
                                onClick = {},
                                shape = CircleShape,
                                backgroundColor = Color(0x33000000),
                                icon = R.drawable.tempimage,
                                pressedIcon = R.drawable.tempimage
                            )
                            IconButton(
                                onClick = {},
                                shape = CircleShape,
                                backgroundColor = Color(0x33000000),
                                icon = R.drawable.tempimage,
                                pressedIcon = R.drawable.tempimage
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun ColorButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonStyle: ButtonStyle = ButtonStyle.Basic,
    onClick: () -> Unit
) {
    val minModifier = when (buttonStyle) {
        ButtonStyle.Big -> Modifier.defaultMinSize(minWidth = 120.dp, minHeight = 56.dp)
        ButtonStyle.Basic -> Modifier.defaultMinSize(minWidth = 67.dp, minHeight = 40.dp)
        ButtonStyle.Small -> Modifier.defaultMinSize(minWidth = 46.dp, minHeight = 28.dp)
        ButtonStyle.Dialog -> Modifier.defaultMinSize(minWidth = 96.dp, minHeight = 40.dp)
    }

    val shape = when (buttonStyle) {
        ButtonStyle.Big -> MaterialTheme.shapes.large
        ButtonStyle.Basic -> MaterialTheme.shapes.medium
        ButtonStyle.Small -> MaterialTheme.shapes.small
        ButtonStyle.Dialog -> MaterialTheme.shapes.small
    }

    val textStyle = when (buttonStyle) {
        ButtonStyle.Big -> TextStyle(
            color = MaterialTheme.colors.background1Color,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        ButtonStyle.Basic, ButtonStyle.Dialog -> TextStyle(
            color = MaterialTheme.colors.background1Color,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        ButtonStyle.Small -> TextStyle(
            color = MaterialTheme.colors.background1Color,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val colors = ButtonDefaults.buttonColors(
        backgroundColor = if (pressed) MaterialTheme.colors.mainColor else MaterialTheme.colors.point1Color,
        disabledBackgroundColor = MaterialTheme.colors.disableColor
    )

    Button(
        onClick = onClick,
        modifier = minModifier.composed { modifier },
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors,
        shape = shape
    ) {
        Text(text = text, style = textStyle)
    }
}

@Composable
fun CustomColorButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    shape: Shape = RoundedCornerShape(8.dp),
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colors.background1Color,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    ),
    buttonColor: Color = MaterialTheme.colors.point1Color,
    buttonPressedColor: Color = MaterialTheme.colors.mainColor,
    optionComposable: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    val minModifier = Modifier.defaultMinSize(minWidth = 10.dp, minHeight = 10.dp)

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val colors = ButtonDefaults.buttonColors(
        backgroundColor = if (pressed) buttonPressedColor else buttonColor,
        disabledBackgroundColor = MaterialTheme.colors.disableColor
    )

    Button(
        onClick = onClick,
        modifier = minModifier.composed { modifier },
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors,
        shape = shape
    ) {
        Row {
            optionComposable?.invoke()
            Text(text = text, style = textStyle)
        }
    }
}

@Composable
fun GrayColorButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    buttonStyle: ButtonStyle = ButtonStyle.Big,
    onClick: () -> Unit
) {

    val minModifier = when (buttonStyle) {
        ButtonStyle.Big -> Modifier.defaultMinSize(minWidth = 120.dp, minHeight = 56.dp)
        ButtonStyle.Basic -> Modifier.defaultMinSize(minWidth = 67.dp, minHeight = 40.dp)
        ButtonStyle.Small -> Modifier.defaultMinSize(minWidth = 46.dp, minHeight = 28.dp)
        ButtonStyle.Dialog -> Modifier.defaultMinSize(minWidth = 96.dp, minHeight = 40.dp)
    }

    val shape = when (buttonStyle) {
        ButtonStyle.Big -> MaterialTheme.shapes.large
        ButtonStyle.Basic -> MaterialTheme.shapes.medium
        ButtonStyle.Small -> MaterialTheme.shapes.small
        ButtonStyle.Dialog -> MaterialTheme.shapes.small
    }

    val textStyle = when (buttonStyle) {
        ButtonStyle.Big -> TextStyle(
            color = MaterialTheme.colors.background1Color,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        ButtonStyle.Basic, ButtonStyle.Dialog -> TextStyle(
            color = MaterialTheme.colors.background1Color,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        ButtonStyle.Small -> TextStyle(
            color = MaterialTheme.colors.background1Color,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val colors = ButtonDefaults.buttonColors(
        backgroundColor = if (pressed) MaterialTheme.colors.grayPressedColor else MaterialTheme.colors.grayColor,
        disabledBackgroundColor = MaterialTheme.colors.disableColor
    )

    Button(
        onClick = onClick,
        modifier = minModifier.composed { modifier },
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors,
        shape = shape
    ) {
        Text(text = text, style = textStyle)
    }
}

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    buttonStyle: ButtonStyle = ButtonStyle.Big,
    onClick: () -> Unit
) {
    val minModifier = when (buttonStyle) {
        ButtonStyle.Small -> Modifier.defaultMinSize(minWidth = 54.dp, minHeight = 28.dp)
        else -> Modifier.defaultMinSize(minWidth = 120.dp, minHeight = 40.dp)
    }

    val shape = when (buttonStyle) {
        ButtonStyle.Small -> RoundedCornerShape(14.dp)
        else -> RoundedCornerShape(30.dp)
    }

    val borderStroke =
        if (enabled) BorderStroke(1.dp, MaterialTheme.colors.mainColor)
        else BorderStroke(1.dp, MaterialTheme.colors.disableColor)

    val interactionSource = remember { MutableInteractionSource() }

    val clickable = Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        role = Role.Button,
        onClick = onClick
    )

    val pressed by interactionSource.collectIsPressedAsState()

    val color =
        if (enabled)
            if (pressed) MaterialTheme.colors.mainColor
            else MaterialTheme.colors.background1Color
        else MaterialTheme.colors.background1Color


    val textStyle = when (buttonStyle) {
        ButtonStyle.Small -> TextStyle(
            color = if (enabled) {
                if (pressed)
                    MaterialTheme.colors.background1Color
                else
                    MaterialTheme.colors.mainColor
            } else
                MaterialTheme.colors.disableColor,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        else -> TextStyle(
            color = if (enabled) {
                if (pressed)
                    MaterialTheme.colors.background1Color
                else
                    MaterialTheme.colors.mainColor
            } else
                MaterialTheme.colors.disableColor,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }

    Box(
        Modifier
            .then(minModifier)
            .background(
                color = color,
                shape = shape
            )
            .border(borderStroke, shape)
            .clip(shape)
            .then(modifier)
            .then(clickable),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true
    ) {
        Row(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = text, style = textStyle, maxLines = 1)
        }
    }
}

@Composable
fun RoundImageButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit,
) {
    val minModifier = Modifier.defaultMinSize(minWidth = 63.dp, minHeight = 28.dp)

    val shape = RoundedCornerShape(14.dp)

    val borderStroke =
        if (enabled) BorderStroke(1.dp, MaterialTheme.colors.mainColor)
        else BorderStroke(1.dp, MaterialTheme.colors.disableColor)

    val interactionSource = remember { MutableInteractionSource() }

    val clickable = Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        role = Role.Button,
        onClick = onClick
    )

    val pressed by interactionSource.collectIsPressedAsState()

    val color = if (enabled)
        if (pressed) MaterialTheme.colors.mainColor else MaterialTheme.colors.background1Color
    else MaterialTheme.colors.background1Color


    val textStyle = TextStyle(
        color = if (enabled) {
            if (pressed)
                MaterialTheme.colors.background1Color
            else
                MaterialTheme.colors.mainColor
        } else
            MaterialTheme.colors.disableColor,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        textAlign = TextAlign.Center
    )

    val painter =
        if (pressed) painterResource(id = R.drawable.tempimage) else painterResource(id = R.drawable.tempimage)

    Box(
        Modifier
            .then(minModifier)
            .background(
                color = color,
                shape = shape
            )
            .border(borderStroke, shape)
            .clip(shape)
            .then(modifier)
            .then(clickable),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = true
    ) {
        Row(
            modifier = modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = text, style = textStyle, maxLines = 1)
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier.padding(start = 4.dp),
                alpha = if (enabled) DefaultAlpha else ContentAlpha.disabled
            )
        }
    }
}

@Composable
fun ChoiceButton(
    selected: Boolean,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val selectableModifier = Modifier.selectable(
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        role = Role.RadioButton,
        interactionSource = interactionSource,
        indication = null
    )

    val color = if (enabled) {
        if (selected) MaterialTheme.colors.point1Color else MaterialTheme.colors.unfocusedColor
    } else MaterialTheme.colors.unfocusedColor

    val borderColor =
        if (enabled) {
            if (selected) MaterialTheme.colors.point1Color else Color(0xFFC9C9C9)
        } else MaterialTheme.colors.disableColor

    val textStyle = TextStyle(
        color = if (enabled) {
            if (selected) MaterialTheme.colors.background1Color else MaterialTheme.colors.sub1Color
        } else MaterialTheme.colors.disableColor,
        fontSize = 18.sp
    )

    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = Modifier
            .defaultMinSize(84.dp, 40.dp)
            .background(color = color, shape = shape)
            .border(BorderStroke(1.dp, borderColor), shape)
            .clip(shape)
            .composed { selectableModifier },
        contentAlignment = Alignment.Center
        //.clickable(enabled = enabled) { onClick }
    ) {
        Text(text = text, style = textStyle, textAlign = TextAlign.Center)
    }
}

@Composable
fun TopBarButton(
    enabled: Boolean = true,
    @DrawableRes icon: Int,
    @DrawableRes pressedIcon: Int,
    text: String,
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit
) {
    val modifier = Modifier.defaultMinSize(minWidth = 120.dp, minHeight = 60.dp)

    val shape = RectangleShape

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val colors = ButtonDefaults.buttonColors(
        backgroundColor = if (pressed)
            MaterialTheme.colors.topBarBtnBackground2Color else backgroundColor,
        disabledBackgroundColor = if (backgroundColor == MaterialTheme.colors.topBarBtnBackground1Color)
            MaterialTheme.colors.sub1ColorDis else Color.Transparent
    )

    val textStyle = TextStyle(
        color = if (enabled) {
            if (pressed)
                MaterialTheme.colors.background1Color
            else
                if (backgroundColor == Color.Transparent) MaterialTheme.colors.sub1Color
                else MaterialTheme.colors.background1Color
        } else {
            if (backgroundColor == MaterialTheme.colors.topBarBtnBackground1Color)
                MaterialTheme.colors.background1Color
            else
                MaterialTheme.colors.sub1ColorDis
        },
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )

    val painter = if (pressed) painterResource(id = pressedIcon) else painterResource(id = icon)

    TextButton(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        modifier = modifier,
        shape = shape,
        colors = colors,
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.padding(end = 8.dp),
            alpha = if (enabled) DefaultAlpha else ContentAlpha.disabled
        )
        Text(text = text, style = textStyle)
    }
}

@Composable
fun TopNavigationButton(
    enabled: Boolean = true,
    @DrawableRes icon: Int,
    @DrawableRes pressedIcon: Int,
    onClick: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val clickableModifier = Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        role = Role.Button,
        onClick = onClick
    )

    val pressed by interactionSource.collectIsPressedAsState()
    val color = if (enabled) {
        if (pressed) MaterialTheme.colors.point1Color else Color.Transparent
    } else Color.Transparent

    val painter = if (pressed) painterResource(id = pressedIcon) else painterResource(id = icon)

    val shape = RectangleShape

    Box(
        Modifier
            .defaultMinSize(40.dp, 40.dp)
            .padding(start = 12.dp, top = 10.dp, bottom = 10.dp)
            .background(
                color = color,
                shape = shape
            )
            .clip(shape)
            .then(clickableModifier),
        propagateMinConstraints = true
    ) {
        Image(
            painter = painter,
            modifier = Modifier
                .defaultMinSize(24.dp, 24.dp)
                .padding(8.dp),
            contentDescription = "",
            contentScale = ContentScale.Inside,
            alpha = if (enabled) DefaultAlpha else ContentAlpha.disabled
        )
    }
}

@Composable
fun IconToggleButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onToggle: Boolean,
    @DrawableRes icon: Int,
    @DrawableRes pressedIcon: Int,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val clickableModifier = Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        role = Role.Checkbox,
        onClick = onClick
    )

    val pressed by interactionSource.collectIsPressedAsState()
    val color = if (enabled) {
        if (pressed || onToggle) MaterialTheme.colors.btnBackground1Color else Color.Transparent
    } else Color.Transparent

    val painter = if (onToggle) painterResource(id = pressedIcon) else painterResource(id = icon)

    Box(
        Modifier
            .then(modifier)
            .defaultMinSize(40.dp, 40.dp)
            .background(
                color = Color.Transparent,
                shape = RectangleShape
            )
            .clip(RectangleShape)
            .then(clickableModifier),
        propagateMinConstraints = true
    ) {

        Canvas(Modifier.alpha(if (enabled) DefaultAlpha else ContentAlpha.disabled)) {
            drawCircle(
                color,
                20.dp.toPx(),
                style = Fill
            )
        }

        Image(painter = painter, contentDescription = "", contentScale = ContentScale.Inside)
    }
}

@Composable
fun MainNewButton(
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit
) {
    val modifier = Modifier
        .defaultMinSize(minWidth = 300.dp, minHeight = 96.dp)

    val shape = RoundedCornerShape(16.dp)

    val interactionSource = remember { MutableInteractionSource() }

    val clickableModifier = Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        role = Role.Button,
        onClick = onClick
    )

    val pressed by interactionSource.collectIsPressedAsState()
    val color = if (enabled) {
        if (pressed) MaterialTheme.colors.point1Color else MaterialTheme.colors.mainColor
    } else MaterialTheme.colors.background1Color

    val textStyle = TextStyle(
        color = if (enabled) MaterialTheme.colors.background1Color else MaterialTheme.colors.disableColor,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )

    Box(
        modifier = clickableModifier
            .then(modifier)
            .background(color = color, shape = shape)
            .clip(shape),
    ) {
        Box(modifier = modifier.padding(24.dp)) {
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                Image(
                    painter = painterResource(id = R.drawable.tempimage),
                    contentDescription = "",
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(text = text, style = textStyle)
            }

            Image(
                painter = painterResource(id = R.drawable.tempimage),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
fun ElectronicTabButton(
    selected: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val selectableModifier = modifier.selectable(
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        role = Role.RadioButton,
        interactionSource = interactionSource,
        indication = null
    )

    val color = if (selected) MaterialTheme.colors.topBarBtnBackground1Color else Color.Transparent

    val shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

    val textStyle =
        (if (selected) MaterialTheme.typography.h4 else MaterialTheme.typography.h5).copy(
            color = if (enabled) {
                if (selected) MaterialTheme.colors.background1Color else MaterialTheme.colors.divider2Color
            } else MaterialTheme.colors.disableColor,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center
        )

    Box(
        Modifier
            .defaultMinSize(minWidth = 164.dp)
            .fillMaxHeight()
            .then(modifier)
            .background(
                color = color,
                shape = shape
            )
            .clip(shape)
            .then(selectableModifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(start = 24.dp, top = 6.dp, end = 24.dp, bottom = 9.dp),
            style = textStyle
        )
    }
}

@Composable
fun MainToggleButton(
    selected: Boolean,
    modifier: Modifier = Modifier,
    hasBorder: Boolean = false,
    enabled: Boolean = true,
    @DrawableRes icon: Int? = null,
    @DrawableRes selectedIcon: Int? = null,
    text: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val selectableModifier = Modifier.selectable(
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        role = Role.RadioButton,
        interactionSource = interactionSource,
        indication = null
    )

    val color = if (selected) MaterialTheme.colors.point1Color else Color.Transparent

    val shape = RoundedCornerShape(4.dp)

    val imageId = if (selected) selectedIcon else icon

    val textStyle = TextStyle(
        color = if (enabled) {
            if (selected) MaterialTheme.colors.background1Color else MaterialTheme.colors.sub1Color
        } else MaterialTheme.colors.disableColor,
        fontSize = 16.sp
    )

    Box(
        Modifier
            .defaultMinSize(164.dp, 40.dp)
            .then(modifier)
            .background(
                color = color,
                shape = shape
            )
            .clip(shape)
            .then(selectableModifier),
        propagateMinConstraints = true
    ) {

        Row(
            modifier = Modifier.padding(top = 6.dp, bottom = 7.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            imageId?.let {
                Image(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = it),
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    alpha = if (enabled) DefaultAlpha else ContentAlpha.disabled
                )
            }
//            Image(
//                painter = painterResource(id = imageId),
//                contentDescription = "",
//                contentScale = ContentScale.Inside,
//                alpha = if (enabled) DefaultAlpha else ContentAlpha.disabled
//            )

            Text(text = text, style = textStyle)
        }
    }
}


@Composable
fun CircleImageButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes icon: Int,
    @DrawableRes pressedIcon: Int,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val clickableModifier = Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        role = Role.Button,
        onClick = onClick
    )

    val pressed by interactionSource.collectIsPressedAsState()
    val color = if (enabled) {
        if (pressed) MaterialTheme.colors.point1Color else Color.Transparent
    } else Color.Transparent

    val painter = if (pressed) painterResource(id = pressedIcon) else painterResource(id = icon)

    Box(
        Modifier
            .then(modifier)
            .defaultMinSize(40.dp, 40.dp)
            .background(
                color = Color.Transparent,
                shape = RectangleShape
            )
            .clip(RectangleShape)
            .then(clickableModifier),
        propagateMinConstraints = true
    ) {
        Canvas(Modifier.alpha(if (enabled) DefaultAlpha else ContentAlpha.disabled)) {
            drawCircle(
                color,
                20.dp.toPx(),
                style = Fill
            )
        }

        Image(painter = painter, contentDescription = "", contentScale = ContentScale.Inside)
    }
}

@Composable
fun PageControlButton(
    pagingButtonStyle: PagingButtonStyle,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
) {
    val clickableModifier = Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        role = Role.Button,
        onClick = onClick
    )

    val pressed by interactionSource.collectIsPressedAsState()
    val borderStroke =
        if (enabled) {
            if (pressed) {
                BorderStroke(1.dp, MaterialTheme.colors.point1Color)
            } else {
                BorderStroke(1.dp, MaterialTheme.colors.sub2Color)
            }
        } else BorderStroke(1.dp, MaterialTheme.colors.disableColor)

    val colors = if (enabled) {
        if (pressed)
            MaterialTheme.colors.point1Color
        else
            MaterialTheme.colors.background1Color
    } else MaterialTheme.colors.background1Color

    val imageResource = when (pagingButtonStyle) {
        PagingButtonStyle.Home -> if (pressed) R.drawable.tempimage else R.drawable.tempimage
        PagingButtonStyle.Back -> if (pressed) R.drawable.tempimage else R.drawable.tempimage
        PagingButtonStyle.Forward -> if (pressed) R.drawable.tempimage else R.drawable.tempimage
        PagingButtonStyle.End -> if (pressed) R.drawable.tempimage else R.drawable.tempimage
    }

    val shape = RoundedCornerShape(6.dp)

    Box(
        Modifier
            .defaultMinSize(minWidth = 30.dp, minHeight = 26.dp)
            .then(modifier)
            .background(
                color = colors,
                shape = shape
            )
            .border(borderStroke, shape)
            .clip(shape)
            .then(clickableModifier),
        propagateMinConstraints = true
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "",
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
            alpha = if (enabled) DefaultAlpha else ContentAlpha.disabled
        )
    }
}

@Composable
fun CheckButton(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val onClick = if (onCheckedChange != null) {
        { onCheckedChange(!checked) }
    } else null

    val toggleableModifier =
        if (onClick != null) {
            Modifier.triStateToggleable(
                state = ToggleableState(checked),
                onClick = onClick,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = null
            )
        } else {
            Modifier
        }

    val borderColor =
        if (enabled) {
            if (checked) MaterialTheme.colors.mainColor else MaterialTheme.colors.sub1Color
        } else MaterialTheme.colors.disableColor

    val border = BorderStroke(1.dp, borderColor)
    val shape = RoundedCornerShape(3.dp)

    val textStyle = TextStyle(
        color = if (enabled) {
            if (checked) MaterialTheme.colors.mainColor else MaterialTheme.colors.sub1Color
        } else MaterialTheme.colors.disableColor,
        fontSize = 14.sp
    )

    Box(
        modifier = toggleableModifier
            .background(color = Color.Transparent)
            .then(modifier)
    ) {

        Row(modifier = toggleableModifier) {

            Box(
                modifier
                    .defaultMinSize(20.dp, 20.dp)
                    .then(Modifier.border(border, shape))
                    .background(
                        color = MaterialTheme.colors.background1Color,
                        shape = shape
                    )
                    .clip(shape),
                propagateMinConstraints = true
            ) {
                if (checked)
                    Image(
                        painter = painterResource(id = R.drawable.tempimage),
                        contentDescription = "",
                        modifier = Modifier.padding(2.dp),
                        alpha = if (enabled) DefaultAlpha else ContentAlpha.disabled
                    )
            }

            if (text?.isNotEmpty() == true) {
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                    style = textStyle
                )
            }
        }
    }
}

@Composable
fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null
) {
    val interactionSource = remember { MutableInteractionSource() }

    val selectableModifier = Modifier.selectable(
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        role = Role.RadioButton,
        interactionSource = interactionSource,
        indication = null
    )

    val dotColor = MaterialTheme.colors.point1Color
    val borderColor =
        if (enabled) {
            if (selected) MaterialTheme.colors.mainColor else MaterialTheme.colors.sub1Color
        } else MaterialTheme.colors.disableColor

    val textStyle = TextStyle(
        color = if (enabled) {
            if (selected) MaterialTheme.colors.mainColor else MaterialTheme.colors.sub1Color
        } else MaterialTheme.colors.disableColor,
        fontSize = 14.sp
    )

    Box(
        modifier = Modifier
            .then(selectableModifier)
            .background(color = Color.Transparent)
            .then(modifier)
    ) {

        Row(modifier = selectableModifier) {

            Canvas(
                Modifier
                    .wrapContentSize(Alignment.Center)
                    .requiredSize(20.dp)
                    .alpha(if (enabled) DefaultAlpha else ContentAlpha.disabled)
            ) {
                val strokeWidth = (1.5).dp.toPx()
                drawCircle(
                    borderColor,
                    10.dp.toPx() - strokeWidth / 2,
                    style = Stroke(strokeWidth)
                )

                if (selected)
                    drawCircle(dotColor, 6.dp.toPx() - strokeWidth / 2, style = Fill)
            }

            if (text?.isNotEmpty() == true) {
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                    style = textStyle
                )
            }
        }
    }
}

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    shape: Shape = RectangleShape,
    @DrawableRes icon: Int,
    @DrawableRes pressedIcon: Int,
) {

    val interactionSource = remember { MutableInteractionSource() }

    val clickableModifier = Modifier.clickable(
        interactionSource = interactionSource,
        indication = rememberRipple(bounded = false, radius = 24.dp),
        enabled = enabled,
        role = Role.Button,
        onClick = onClick
    )

    val pressed by interactionSource.collectIsPressedAsState()
    val painter = if (pressed) painterResource(id = pressedIcon) else painterResource(id = icon)

    Box(
        Modifier
            .background(
                color = backgroundColor,
                shape = shape
            )
            .clip(shape)
            .then(modifier)
            .then(clickableModifier),
        propagateMinConstraints = true
    ) {
        Image(
            painter = painter,
            modifier = Modifier.defaultMinSize(24.dp, 24.dp),
            contentDescription = "",
            contentScale = ContentScale.Inside,
            alpha = if (enabled) DefaultAlpha else ContentAlpha.disabled
        )
    }
}

@Composable
fun GrayDialogButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    buttonStyle: ButtonStyle = ButtonStyle.Basic,
) {
    val minModifier = when (buttonStyle) {
        ButtonStyle.Big -> Modifier.defaultMinSize(minWidth = 300.dp, minHeight = 84.dp)
        ButtonStyle.Basic -> Modifier.defaultMinSize(minWidth = 180.dp, minHeight = 60.dp)
        ButtonStyle.Small -> Modifier.defaultMinSize(minWidth = 180.dp, minHeight = 60.dp)
        ButtonStyle.Dialog -> Modifier.defaultMinSize(minWidth = 300.dp, minHeight = 60.dp)
    }

    val textStyle = TextStyle(
        color = MaterialTheme.colors.background1Color,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = if (buttonStyle == ButtonStyle.Big) 32.sp else 24.sp,
        textAlign = TextAlign.Center
    )

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val colors = ButtonDefaults.buttonColors(
        backgroundColor = if (pressed) MaterialTheme.colors.grayPressedColor else MaterialTheme.colors.grayColor,
        disabledBackgroundColor = MaterialTheme.colors.disableColor
    )

    Button(
        onClick = onClick,
        modifier = minModifier.composed { modifier },
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors,
        shape = RoundedCornerShape(0.dp)
    ) {
        Text(text = text, style = textStyle)
    }
}

@Composable
fun ColorDialogButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    buttonStyle: ButtonStyle = ButtonStyle.Basic,
) {
    val minModifier = when (buttonStyle) {
        ButtonStyle.Big -> Modifier.defaultMinSize(minWidth = 300.dp, minHeight = 84.dp)
        ButtonStyle.Basic -> Modifier.defaultMinSize(minWidth = 180.dp, minHeight = 60.dp)
        ButtonStyle.Small -> Modifier.defaultMinSize(minWidth = 180.dp, minHeight = 60.dp)
        ButtonStyle.Dialog -> Modifier.defaultMinSize(minWidth = 300.dp, minHeight = 60.dp)
    }

    val textStyle = TextStyle(
        color = MaterialTheme.colors.background1Color,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = if (buttonStyle == ButtonStyle.Big) 32.sp else 24.sp,
        textAlign = TextAlign.Center
    )

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val colors = ButtonDefaults.buttonColors(
        backgroundColor = if (pressed) MaterialTheme.colors.mainColor else MaterialTheme.colors.point1Color,
        disabledBackgroundColor = MaterialTheme.colors.disableColor
    )

    Button(
        onClick = onClick,
        modifier = minModifier.composed { modifier },
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors,
        shape = RoundedCornerShape(0.dp)
    ) {
        Text(text = text, style = textStyle)
    }
}

sealed class ButtonStyle {
    object Big : ButtonStyle()
    object Basic : ButtonStyle()
    object Small : ButtonStyle()
    object Dialog : ButtonStyle()
}

sealed class PagingButtonStyle {
    object Home : PagingButtonStyle()
    object Back : PagingButtonStyle()
    object Forward : PagingButtonStyle()
    object End : PagingButtonStyle()
}