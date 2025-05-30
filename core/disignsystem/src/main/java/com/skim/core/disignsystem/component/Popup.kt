package com.skim.core.disignsystem.component

import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.skim.core.disignsystem.R
import com.skim.core.disignsystem.theme.SKimTheme
import com.skim.core.disignsystem.theme.background1Color
import com.skim.core.disignsystem.theme.divider1Color
import com.skim.core.disignsystem.theme.point1Color
import com.skim.core.disignsystem.theme.sub1Color
import com.skim.core.disignsystem.theme.toastColor
import com.skim.core.disignsystem.theme.unfocusedColor

@Preview(device = Devices.AUTOMOTIVE_1024p)
@Composable
fun PreviewPopupScreen() {
    SKimTheme {
//        val openDialog = remember { mutableStateOf(true) }
//        val openAlert = remember { mutableStateOf(false) }
//        BasicDialog(
//            openDialog = openDialog.value,
//            titleText = "Title",
//            contentText = "팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요.",
//            leftBtnText = "취소",
//            rightBtnText = "확인",
//            onRightBtnClick = {
//                openDialog.value = false
//            }
//        )
//
//        AlertDialog(
//            openDialog = openAlert.value,
//            onDismissRequest = {},
//            contentText = "팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요.",
//            onRightBtnClick = {
//                openAlert.value = false
//            }
//        )

        Column {
            BasicDialogContent(
                titleText = "알림",
                contentText = buildAnnotatedString { append("팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요.") },
                rightBtnText = "확인",
                onRightBtnClick = {},
                buttonStyle = ButtonStyle.Dialog
            )

            AlertDialogContent(
                content = buildAnnotatedString { append("팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요. 팝업 세부 내용을 입력하세요.") },
                leftBtnText = "취소",
                onRightBtnClick = {}
            )

            Toast(text = "Toast message Toast message Toast message")
        }
    }

}

@Composable
fun TextDialog(
    title: String = "제목 없음",
    content: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    // 전체 화면을 덮는 Box로 Popup을 대체
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // 반투명 배경
            .clickable(
                onClick = { /* 빈 클릭 동작으로 바깥 클릭 무시 */ },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center // 화면 중앙에 위치
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f) // Card 너비 조절
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp), // 모서리 곡선 조정
            backgroundColor = MaterialTheme.colors.background, // MaterialTheme 색상 사용
            elevation = 2.dp
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color = MaterialTheme.colors.sub1Color,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h5
                    )
                }
                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colors.divider1Color
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    SelectionContainer {
                        Text(
                            text = content,
                            style = MaterialTheme.typography.subtitle1,
                        )
                    }
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    GrayDialogButton(
                        onClick = onCancel,
                        text = stringResource(id = R.string.fg_designsystem_cancel),
                        buttonStyle = ButtonStyle.Basic,
                        modifier = Modifier.weight(1f),
                    )

                    ColorDialogButton(
                        onClick = onConfirm,
                        text = stringResource(id = R.string.fg_designsystem_confirm),
                        buttonStyle = ButtonStyle.Basic,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewBasicBottomDialog() {
    SKimTheme {
        BasicBottomDialog(
            "Title",
            modifier = Modifier
                .size(500.dp, 400.dp),
            onClose = {},
            visible = true
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Contents", modifier = Modifier.weight(1f))
                Row(
                    Modifier.height(60.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    GrayDialogButton(
                        onClick = { },
                        text = "취소",
                        modifier = Modifier.weight(1f)
                    )
                    ColorDialogButton(
                        onClick = { },
                        text = "확인",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewTextDialog() {
    SKimTheme {
        TextDialog("title", "{\n" +
                "aaaaa" +
                "}\n", {}, {})
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BasicBottomDialog(
    title: String,
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(0.dp),
    visible: Boolean,
    hideKeyboard: () -> Unit = {},
    topBarButtons: @Composable () -> Unit = {}, // 6 펜, 작성취소, 작성완료
    content: @Composable ColumnScope.() -> Unit
) {
    BottomPopupScope.Transition(visible = visible, customHideKeyboard = hideKeyboard) {
        Column(
            modifier = Modifier
                .then(modifier)
                .animateEnterExit(
                    enter = slideInVertically { it },
                    exit = slideOutVertically { it }
                )
                .shadow(elevation = 2.dp)
                .background(color = MaterialTheme.colors.background1Color, shape = shape)
        ) {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colors.sub1Color,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h5
                )
                if (onClose != null) {
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.micon_cancle),
                            contentDescription = ""
                        )
                    }
                }
                topBarButtons()
            }
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colors.divider1Color
            )
            content()
        }
    }
}

@Composable
fun BasicDialog(
    modifier: Modifier = Modifier,
    titleText: String,
    contentText: String,
    onClosed: () -> Unit = {},
    leftBtnText: String? = null,
    onLeftBtnClick: () -> Unit = {},
    rightBtnText: String,
    onRightBtnClick: () -> Unit,
    onDismissRequest: () -> Unit = {},
    properties: PopupProperties = PopupProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    )
) {
    BasicDialog(
        modifier = modifier,
        titleText = titleText,
        contentText = buildAnnotatedString { append(contentText) },
        onClosed = onClosed,
        leftBtnText = leftBtnText,
        onLeftBtnClick = onLeftBtnClick,
        rightBtnText = rightBtnText,
        onRightBtnClick = onRightBtnClick,
        onDismissRequest = onDismissRequest,
        properties = properties
    )
}

@Composable
fun BasicDialog(
    modifier: Modifier = Modifier,
    titleText: String,
    contentText: AnnotatedString,
    onClosed: () -> Unit = {},
    leftBtnText: String? = null,
    onLeftBtnClick: () -> Unit = {},
    rightBtnText: String,
    onRightBtnClick: () -> Unit,
    onDismissRequest: () -> Unit = {},
    properties: PopupProperties = PopupProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    )
) {
    Popup(
        popupPositionProvider = WindowCenterOffsetPositionProvider(),
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        BasicDialogContent(
            modifier,
            titleText,
            contentText,
            onClosed,
            leftBtnText,
            onLeftBtnClick,
            rightBtnText,
            onRightBtnClick
        )
    }

}

@Composable
fun BasicDialogContent(
    modifier: Modifier = Modifier,
    titleText: String,
    contentText: AnnotatedString,
    onClosed: () -> Unit = {},
    leftBtnText: String? = null,
    onLeftBtnClick: () -> Unit = {},
    rightBtnText: String,
    onRightBtnClick: () -> Unit,
    buttonStyle: ButtonStyle = ButtonStyle.Basic,
) {
    Card(
        modifier = Modifier
            .requiredSizeIn(minWidth = 360.dp, minHeight = 212.dp, maxWidth = 800.dp)
            .padding(20.dp)
            .shadow(elevation = 2.dp)
            .composed { modifier },
        shape = RoundedCornerShape(0.dp),
        backgroundColor = MaterialTheme.colors.background1Color
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 12.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = titleText,
                    style = MaterialTheme.typography.h6.merge(
                        TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.sub1Color
                        )
                    ),
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onClosed,
                    modifier = Modifier.size(40.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.micon_cancle),
                        contentDescription = ""
                    )
                }
            }

            Column(
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 24.dp,
                    end = 24.dp,
                    bottom = 36.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = contentText,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                Modifier.height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                leftBtnText?.let {
                    GrayDialogButton(
                        onClick = onLeftBtnClick,
                        text = leftBtnText,
                        buttonStyle = buttonStyle,
                        modifier = Modifier.weight(1f),
                    )
                }

                ColorDialogButton(
                    onClick = onRightBtnClick,
                    text = rightBtnText,
                    buttonStyle = buttonStyle,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    contentText: String,
    leftBtnText: String? = null,
    onLeftBtnClick: () -> Unit = {},
    rightBtnText: String = stringResource(id = R.string.fg_designsystem_confirm),
    onRightBtnClick: () -> Unit,
    onDismissRequest: () -> Unit = {},
    properties: PopupProperties = PopupProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    )
) {
    AlertDialog(
        modifier = modifier,
        contentText = buildAnnotatedString { append(contentText) },
        leftBtnText = leftBtnText,
        onLeftBtnClick = onLeftBtnClick,
        rightBtnText = rightBtnText,
        onRightBtnClick = onRightBtnClick,
        onDismissRequest = onDismissRequest,
        properties = properties
    )
}

@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    contentText: AnnotatedString,
    leftBtnText: String? = null,
    onLeftBtnClick: () -> Unit = {},
    rightBtnText: String = stringResource(id = R.string.fg_designsystem_confirm),
    onRightBtnClick: () -> Unit,
    onDismissRequest: () -> Unit = {},
    properties: PopupProperties = PopupProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    )
) {
    Popup(
        popupPositionProvider = WindowCenterOffsetPositionProvider(),
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        AlertDialogContent(
            modifier,
            contentText,
            leftBtnText,
            onLeftBtnClick,
            rightBtnText,
            onRightBtnClick
        )
    }
}

@Composable
fun GlobalDialog(
    modifier: Modifier = Modifier,
    contentText: String,
    leftBtnText: String? = null,
    onLeftBtnClick: () -> Unit = {},
    rightBtnText: String = stringResource(id = R.string.fg_designsystem_confirm),
    onRightBtnClick: () -> Unit,
    onDismissRequest: () -> Unit = {},
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    )
){
    GlobalDialog(
        modifier = modifier,
        contentText = buildAnnotatedString { append(contentText) },
        leftBtnText = leftBtnText,
        onLeftBtnClick = onLeftBtnClick,
        rightBtnText = rightBtnText,
        onRightBtnClick = onRightBtnClick,
        onDismissRequest = onDismissRequest,
        properties = properties
    )
}

@Composable
fun GlobalDialog(
    modifier: Modifier = Modifier,
    contentText: AnnotatedString,
    leftBtnText: String? = null,
    onLeftBtnClick: () -> Unit = {},
    rightBtnText: String = stringResource(id = R.string.fg_designsystem_confirm),
    onRightBtnClick: () -> Unit,
    onDismissRequest: () -> Unit = {},
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    )
){
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        AlertDialogContent(
            modifier,
            contentText,
            leftBtnText,
            onLeftBtnClick,
            rightBtnText,
            onRightBtnClick
        )
    }
}

class WindowCenterOffsetPositionProvider(
    private val x: Int = 0,
    private val y: Int = 0
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        return IntOffset(
            (windowSize.width - popupContentSize.width) / 2 + x,
            (windowSize.height - popupContentSize.height) / 2 + y
        )
    }
}

@Composable
fun AlertDialogContent(
    modifier: Modifier = Modifier,
    content: AnnotatedString,
    leftBtnText: String? = null,
    onLeftBtnClick: () -> Unit = {},
    rightBtnText: String = stringResource(id = R.string.fg_designsystem_confirm),
    onRightBtnClick: () -> Unit,
    buttonStyle: ButtonStyle = ButtonStyle.Basic
) {
    Card(
        modifier = Modifier
            .requiredSizeIn(minWidth = 360.dp, minHeight = 100.dp, maxWidth = 500.dp)
            .shadow(elevation = 2.dp)
            .composed { modifier },
        shape = RoundedCornerShape(0.dp),
        backgroundColor = MaterialTheme.colors.background1Color
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 36.dp,
                    end = 24.dp,
                    bottom = 36.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                Modifier.height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                leftBtnText?.let {
                    GrayDialogButton(
                        onClick = onLeftBtnClick,
                        text = leftBtnText,
                        buttonStyle = buttonStyle,
                        modifier = Modifier.weight(1f),
                    )
                }

                ColorDialogButton(
                    onClick = onRightBtnClick,
                    text = rightBtnText,
                    buttonStyle = buttonStyle,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
fun Toast(text: String) {
    Surface(
        modifier = Modifier.defaultMinSize(minWidth = 186.dp, minHeight = 56.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colors.toastColor
    ) {
        Row(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = MaterialTheme.colors.background1Color,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle1
            )
        }

    }
}

@Preview(widthDp = 1280, heightDp = 800)
@Composable
fun PreviewMemoBottomDialog() {
    SKimTheme {
        MemoBottomDialog(
            visible = true,
            memo = "",
            onMemoChange = {},
            onCancel = {},
            onConfirm = {}
        )
    }
}

@Composable
fun MemoBottomDialog(
    visible: Boolean,
    memo: String,
    currentBytes: Int = 0,
    totalBytes: Int = 1000,
    onMemoChange: (String) -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    BasicBottomDialog(
        title = "메모",
        modifier = Modifier
            .width(840.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { },
        onClose = onCancel,
        visible = visible
    ) {
        Column {
            Box(
                modifier = Modifier
                    .padding(36.dp)
                    .fillMaxWidth()
                    .height(264.dp)
            ) {
                OutlinedTextField(
                    value = memo,
                    onValueChange = {
                        onMemoChange(it)
                    },
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = MaterialTheme.colors.unfocusedColor,
                        focusedBorderColor = MaterialTheme.colors.point1Color,
                        cursorColor = MaterialTheme.colors.point1Color
                    )
                )

                Text(
                    text = "$currentBytes / $totalBytes bytes",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp),
                    color = MaterialTheme.colors.sub1Color,
                    style = MaterialTheme.typography.body1
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                GrayDialogButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(0.5f),
                    text = stringResource(id = R.string.fg_designsystem_cancel),
                    buttonStyle = ButtonStyle.Big
                )
                ColorDialogButton(
                    onClick = onConfirm,
                    text = stringResource(id = R.string.fg_designsystem_confirm),
                    modifier = Modifier.weight(0.5f),
                    buttonStyle = ButtonStyle.Big
                )
            }
        }
    }
}

@Preview(widthDp = 1280, heightDp = 800)
@Composable
fun PreviewTestElectronicPopup() {
    TestElectronicPopup(visible = true, onCancel = {}, tBiz = "", tMappings = listOf(), url = "", convert = { _, _ ->  }, onConfirm = { _, _ -> })
}

@Composable
fun TestElectronicPopup(
    visible: Boolean,
    url: String,
    tBiz: String,
    tMappings: List<Pair<String, String>>,
    convert: (content: String, result: (data: List<Pair<String, String>>) -> Unit) -> Unit,
    onCancel: () -> Unit,
    onConfirm: (biz: String, mappings: List<Pair<String, String>>) -> Unit
) {
    var biz by remember { mutableStateOf(tBiz) }

    val mappings = remember { mutableStateListOf<Pair<String, String>>().apply { addAll(tMappings) } }

    val jsonContents = remember { mutableStateOf("") }

    BasicBottomDialog(
        title = "전자문서 테스트 호출",
        modifier = Modifier
            .width(1000.dp)
            .padding(10.dp),
        visible = visible,
        topBarButtons = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TopBarDivider()
                TopBarButton(
                    icon = R.drawable.micon_cancle,
                    pressedIcon = R.drawable.micon_cancle_on,
                    text = "취소",
                    onClick = { onCancel() }
                )
                TopBarDivider()
                TopBarButton(
                    icon = R.drawable.micon_check,
                    pressedIcon = R.drawable.micon_check_on,
                    text = "확인",
                    onClick = { onConfirm(biz, mappings) }
                )
            }
        }
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Card {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            WebView(context).apply {
                                settings.apply {
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                    javaScriptEnabled = true
                                    domStorageEnabled = true
                                    cacheMode = WebSettings.LOAD_NO_CACHE
                                }

                                webViewClient = WebViewClient()

                                loadUrl(url)
                            }
                        }
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                color = MaterialTheme.colors.divider1Color
            )

            Column (
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "비즈로직")

                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                        value = biz,
                        onValueChange = { biz = it },
                        shape = RectangleShape,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = MaterialTheme.colors.unfocusedColor,
                            focusedBorderColor = MaterialTheme.colors.point1Color,
                            cursorColor = MaterialTheme.colors.point1Color
                        )
                    )
                }

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Button(
                        onClick = {
                            mappings.clear()
                            convert(jsonContents.value) {
                                mappings.addAll(it)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    ) { Text("Json -> Object") }

                    OutlinedTextField(
                        singleLine = false,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Default
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {}
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                        value = jsonContents.value,
                        onValueChange = { newValue ->
                            jsonContents.value = newValue
                        },
                        shape = RectangleShape,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = MaterialTheme.colors.unfocusedColor,
                            focusedBorderColor = MaterialTheme.colors.point1Color,
                            cursorColor = MaterialTheme.colors.point1Color
                        )
                    )
                }

                Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "매핑 데이터")
                    // Add button
                    Button(
                        onClick = {
                            mappings.add("" to "") // Add empty pair
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp)
                    ) { Text("추가") }
                }

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Index",
                            modifier = Modifier.width(50.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "key",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "value",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "",
                            modifier = Modifier.width(100.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    // Dynamic list of mappings
                    mappings.forEachIndexed { index, pair ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.width(50.dp),
                                text = "$index",
                                textAlign = TextAlign.Center
                            )

                            OutlinedTextField(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 4.dp),
                                value = pair.first,
                                onValueChange = { newValue ->
                                    mappings[index] = pair.copy(first = newValue)
                                },
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = MaterialTheme.colors.unfocusedColor,
                                    focusedBorderColor = MaterialTheme.colors.point1Color,
                                    cursorColor = MaterialTheme.colors.point1Color
                                )
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .weight(1f),
                                value = pair.second,
                                onValueChange = { newValue ->
                                    mappings[index] = pair.copy(second = newValue)
                                },
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = MaterialTheme.colors.unfocusedColor,
                                    focusedBorderColor = MaterialTheme.colors.point1Color,
                                    cursorColor = MaterialTheme.colors.point1Color
                                )
                            )
                            Button(
                                onClick = { mappings.removeAt(index) }, // Remove this mapping
                                modifier = Modifier
                                    .width(100.dp)
                                    .padding(start = 4.dp)
                            ) {
                                Text("제거")
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}


object BottomPopupScope {

    @Composable
    fun Transition(
        visible: Boolean,
        customHideKeyboard: () -> Unit = {},
        content: @Composable AnimatedVisibilityScope.() -> Unit
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(initialAlpha = 1f),
            exit = fadeOut()
        ) {

            val focusManager = LocalFocusManager.current

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x33000000))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        customHideKeyboard()
                        focusManager.clearFocus()
                    },
                contentAlignment = Alignment.BottomCenter
            ) {
                content()
            }
        }
    }

}