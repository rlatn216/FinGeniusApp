package com.skim.core.designsystem.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.skim.core.designsystem.theme.SKimColor.Point1Color

private val SKImDefaultColorPalette = lightColors(
    primary = Point1Color,
    surface = Point1Color,
)

@Composable
fun SKimTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = SKImDefaultColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

val Colors.mainColor: Color
    get() = SKimColor.MainColor

val Colors.background1Color: Color
    get() = SKimColor.Background1Color

val Colors.background2Color: Color
    get() = SKimColor.Background2Color

val Colors.background3Color: Color
    get() = SKimColor.Background3Color

val Colors.background4Color: Color
    get() = SKimColor.Background4Color

val Colors.sub1Color: Color
    get() = SKimColor.Sub1Color

val Colors.sub1ColorDis: Color
    get() = SKimColor.Sub1ColorDis

val Colors.sub2Color: Color
    get() = SKimColor.Sub2Color

val Colors.text1Color: Color
    get() = SKimColor.Text1Color

val Colors.text2Color: Color
    get() = SKimColor.Text2Color

val Colors.point1Color: Color
    get() = SKimColor.Point1Color

val Colors.point2Color: Color
    get() = SKimColor.Point2Color

val Colors.point3Color: Color
    get() = SKimColor.Point3Color

val Colors.point4Color: Color
    get() = SKimColor.Point4Color

val Colors.point5Color: Color
    get() = SKimColor.Point5Color

val Colors.point6Color: Color
    get() = SKimColor.Point6Color

val Colors.point7Color: Color
    get() = SKimColor.Point7Color

val Colors.point8Color: Color
    get() = SKimColor.Point8Color

val Colors.point9Color: Color
    get() = SKimColor.Point9Color

val Colors.point10Color: Color
    get() = SKimColor.Point10Color

val Colors.unfocusedColor: Color
    get() = SKimColor.UnfocusedColor

val Colors.disableColor: Color
    get() = SKimColor.DisableColor

val Colors.toolTipColor: Color
    get() = SKimColor.ToolTipCoor

val Colors.grayColor: Color
    get() = SKimColor.GrayColor

val Colors.grayPressedColor: Color
    get() = SKimColor.GrayPressedColor

val Colors.toastColor: Color
    get() = SKimColor.ToastColor

val Colors.divider1Color: Color
    get() = SKimColor.Divider1Color

val Colors.divider2Color: Color
    get() = SKimColor.Divider2Color

val Colors.errorColor: Color
    get() = SKimColor.ErrorColor

val Colors.btnBackground1Color: Color
    get() = SKimColor.BtnBackground1Color

val Colors.topBarBtnBackground1Color: Color
    get() = SKimColor.TopBarBtnBackground1Color

val Colors.topBarBtnBackground2Color: Color
    get() = SKimColor.TopBarBtnBackground2Color

val Colors.topBarBtnBubbleBackground1Color: Color
    get() = SKimColor.TopBarBtnBubbleBackground1Color