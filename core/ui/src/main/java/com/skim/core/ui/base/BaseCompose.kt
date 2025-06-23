package com.skim.core.ui.base

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skim.core.designsystem.theme.SKimTheme
import com.skim.core.model.BaseConfig
import com.skim.core.ui.R


class BaseCompose() {
    var topBar: @Composable (() -> Unit)? = null
    var content: @Composable () -> Unit = {}
    var surface: @Composable (() -> Unit)? = null
    var bottomBar: @Composable (() -> Unit)? = null

    val baseScreen: @Composable () -> Unit = {
        SKimTheme {
            BackHandler(true) {
                // Back Key를 기본적으로 막음.
            }
            Scaffold(
                topBar = { topBar?.invoke() },
                content = { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        content.invoke()
                    }
                },
                bottomBar = { bottomBar?.invoke() }
            )

            // 현재 버전 상태 info UI
            if (stringResource(id = R.string.env_name).isNotBlank()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(id = R.string.env_name) + " / v" + BaseConfig.VERSION_NAME,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp),
                        color = colorResource(id = R.color.env_name),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            color = Color.Black, shadow = Shadow(
                                color = Color.Black, blurRadius = 2f
                            )
                        )
                    )
                }
            }
            surface?.invoke()
        }
    }
}

