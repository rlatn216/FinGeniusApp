package com.skim.core.designsystem.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FloatingDebugLog(
    isShowFloatingLog: Boolean,
    mainLogLines: List<String>,
    curLogLines: List<String>,
    listState: LazyListState,
    filterType: String,
    onFilterType: (String) -> Unit,
    isAutoPaused: Boolean,
    isLogPaused: Boolean,
    onLogPause: () -> Unit,
    onClear: () -> Unit,
    message: String = "",
    errorMessage: String = "",
    onErrorMessage: (String) -> Unit
) {
    val density = LocalDensity.current
    var offsetX by remember { mutableStateOf(with(density) { -30.dp.toPx() }) }
    var offsetY by remember { mutableStateOf(with(density) { 180.dp.toPx() }) }
    var extended by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    var isDebugScreenMaxSize by remember { mutableStateOf(false) }
    var debugMessage by remember { mutableStateOf(message) }
    var infoMessageString by remember { mutableStateOf("") }
    var isLogAdd by remember { mutableStateOf(false) }

    var delayJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(mainLogLines.size) {
        delayJob?.cancel() //
        isLogAdd = true
        delayJob = launch {
            delay(100)
            isLogAdd = false
        }
    }

    LaunchedEffect(extended, filterType, curLogLines.size) {
        val targetIndex = curLogLines.size - 1
        if (targetIndex in 0 until curLogLines.size) {
            listState.scrollToItem(targetIndex)
        }
    }

    infoMessageString =
        if (filterType == "WebView") "[object Object] 변수 확인을 위해서는 웹에서 JSON.stringify로 감싸주세요."
        else if (filterType == "BizLogic") "비즈로직 오류 발생 시 태블릿 조작을 하지 않아도 로그가 찍힙니다. 유의하세요"
        else ""

    Box(modifier = Modifier.fillMaxSize()) {
        if (isShowFloatingLog) {
            Popup(
                alignment = Alignment.TopEnd,
                offset = IntOffset(x = offsetX.roundToInt(), y = offsetY.roundToInt())
            ) {
                Box(modifier = Modifier
                    .padding(5.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                            // 화면 밖으로 나가는거 방지
                            if (offsetX > 0) {
                                offsetX = 0F
                            }
                            if (offsetY < 0) {
                                offsetY = 0F
                            }
                            if (-offsetX.toDp() > screenWidth.dp) {
                                offsetX = -screenWidth.dp.toPx()
                            }
                            if (offsetY.toDp() > screenHeight.dp) {
                                offsetY = screenHeight.dp.toPx()
                            }
                        }
                    }
                    .defaultMinSize(60.dp, 60.dp)
                ) {
                    Column(
                        modifier = if (extended) if (isDebugScreenMaxSize) Modifier
                            .fillMaxSize()
                            .background(Color(0x88000000))
                            .clickable { extended = !extended }
                        else Modifier
                            .size(700.dp, 600.dp)
                            .background(Color(0x88000000))
                            .clickable { extended = !extended } else Modifier.size(0.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            ColorButton(
                                text = "ALL",
                                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                                enabled = filterType != "ALL",
                                onClick = { onFilterType("ALL") }
                            )
                            ColorButton(
                                text = "WebView",
                                modifier = Modifier.padding(8.dp),
                                enabled = filterType != "WebView",
                                onClick = { onFilterType("WebView") }
                            )
                            ColorButton(
                                text = "BizLogic",
                                modifier = Modifier.padding(8.dp),
                                enabled = filterType != "BizLogic",
                                onClick = { onFilterType("BizLogic") }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            ColorButton(
                                text = "로그Clear",
                                modifier = Modifier.padding(8.dp),
                                onClick = onClear
                            )
                            ColorButton(
                                text = if (isLogPaused) "로그시작" else "로그멈춤",
                                modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                                onClick = onLogPause
                            )
                            ColorButton(
                                text = "전체화면",
                                modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                                onClick = { isDebugScreenMaxSize = !isDebugScreenMaxSize }
                            )
                        }
                        AnimatedVisibility(infoMessageString != "") {
                            Column(
                                modifier = Modifier
                                    .shadow(
                                        if (extended) 0.dp else 3.dp,
                                        if (extended) RoundedCornerShape(0.dp) else CircleShape
                                    )
                                    .clip(if (extended) RoundedCornerShape(0.dp) else CircleShape)
                                    .background(Color(0xFFFFD180))
                                    .clickable { infoMessageString = "" },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    modifier = Modifier.padding(10.dp),
                                    text = infoMessageString, color = Color.Black,
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    animateColorAsState(
                                        targetValue = if (isLogPaused) Color.Black else Color(
                                            0x4F000000
                                        ),
                                        animationSpec = tween(durationMillis = 500), label = ""
                                    ).value
                                )
                                .padding(16.dp)
                                .simpleVerticalScrollbar(listState)
                        ) {
                            items(curLogLines.size) { index ->
                                val line = curLogLines[index]

                                val textColor =
                                    if (line.contains("WebInterface")
                                        || line.contains("responseNative")
                                    ) Color.Cyan
                                    else if (line.contains("logState")) Color(0xFFD6A8FF)
                                    else if (line.contains(" E/")) Color(0xFFFF0B00)
                                    else if (line.contains("QService")) Color(0xFFC4FFA0)
                                    else if (line.contains("OkHttp")) Color(0xFFFFE7A0)
                                    else if (line.contains("paperless")) Color(0xFFA0DEFF)
                                    else if (line.contains("[BIZ]")) Color(0xFFFFFF8D)
                                    else Color.White

                                Text(
                                    text = line,
                                    modifier = Modifier.padding(top = 2.dp),
                                    color = textColor,
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (debugMessage != "") {
                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .shadow(
                                            if (extended) 0.dp else 3.dp,
                                            if (extended) RoundedCornerShape(0.dp) else CircleShape
                                        )
                                        .clip(if (extended) RoundedCornerShape(0.dp) else CircleShape)
                                        .background(Color(0xFFFFD180))
                                        .clickable { debugMessage = "" },
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        modifier = Modifier.padding(10.dp),
                                        text = debugMessage,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.body2
                                    )
                                }
                            }
                            if (errorMessage != "") {
                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .shadow(
                                            if (extended) 0.dp else 3.dp,
                                            if (extended) RoundedCornerShape(0.dp) else CircleShape
                                        )
                                        .clip(if (extended) RoundedCornerShape(0.dp) else CircleShape)
                                        .background(Color(0xFFFF0000))
                                        .clickable { onErrorMessage("") },
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        modifier = Modifier.padding(10.dp),
                                        text = errorMessage,
                                        color = Color.White,
                                        style = MaterialTheme.typography.body2
                                    )
                                }
                            }
                        }
                        Column(
                            modifier = if (!extended) Modifier
                                .shadow(
                                    if (extended) 0.dp else 3.dp,
                                    if (extended) RoundedCornerShape(0.dp) else CircleShape
                                )
                                .clip(if (extended) RoundedCornerShape(0.dp) else CircleShape)
                                .size(60.dp, 60.dp)
                                .background(
                                    if (isLogPaused) Color(0xFF000000)
                                    else if (isAutoPaused) Color(0xFF790000)
                                    else if (isLogAdd) Color(0xFFDA3B3B)
                                    else Color(0xFFB5B8CA)
                                )
                                .clickable { extended = !extended } else Modifier.size(0.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            if (isLogPaused) {
                                Text(
                                    text = "중지됨",
                                    color = Color.White,
                                    style = MaterialTheme.typography.body2,
                                )
                            } else if (isAutoPaused) {
                                Text(
                                    text = "Auto",
                                    color = Color.White,
                                    style = MaterialTheme.typography.body2
                                )
                                Text(
                                    text = "Paused",
                                    color = Color.White,
                                    style = MaterialTheme.typography.body2
                                )
                            } else if (isLogAdd) {
                                Text(
                                    text = "연산중",
                                    color = Color.White,
                                    style = MaterialTheme.typography.body2
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = "Info Icon",
                                    modifier = Modifier.size(32.dp),
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 2.dp
): Modifier = composed {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration), label = ""
    )

    drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (needDrawScrollbar && firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = Color.Red,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}