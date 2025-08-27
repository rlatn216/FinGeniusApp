package com.skim.main.splash.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skim.core.designsystem.theme.background1Color
import com.skim.core.designsystem.theme.sub1Color
import com.skim.main.splash.R

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 1280, heightDp = 800)
@Composable
fun SplashContentLayoutPreview(){
    SplashContentLayout(true)
}

@Composable
fun SplashContentLayout(showProgressState: Boolean) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background1Color)
            .fillMaxSize(),
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Row {
//                Image(
//                    modifier = Modifier.height(45.dp),
//                    painter = painterResource(id = com.skim.core.designsystem.R.drawable.logo_c),
//                    contentDescription = null,
//                    contentScale = ContentScale.FillHeight
//                )

                Text(
                    modifier = Modifier
                        .height(45.dp)
                        .padding(start = 18.dp)
                        .background(Color(0xFF131B63), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp),
                    text = stringResource(com.skim.core.ui.R.string.skim_ui_title_message),
                    style = MaterialTheme.typography.h2,
                    color = Color.White
                )
            }

            Text(
                modifier = Modifier.padding(top = 153.dp),
                text = stringResource(R.string.skim_splash_vaccine_processing_message),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.sub1Color,
                textAlign = TextAlign.Center
            )
            if (showProgressState) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 48.dp))
            }
        }
    }
}