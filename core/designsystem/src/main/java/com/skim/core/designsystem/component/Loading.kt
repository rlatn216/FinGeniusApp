package com.skim.core.designsystem.component

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.skim.core.disignsystem.R
import com.skim.core.designsystem.theme.background1Color
import com.skydoves.landscapist.glide.GlideImage

@Preview(heightDp = 300)
@Composable
fun PreviewLoadingPopup() {
    LoadingPopup(message = "로딩중...")
}

@Preview(heightDp = 300)
@Composable
fun PreviewLoadingProgressPopup() {
    LoadingPopup(message = "로딩중...", progress = 0.5f)
}

@Preview(heightDp = 300)
@Composable
fun PreviewLoadingProgressPopup2() {
    LoadingPopup(message = "로딩중...", progress = 0.5f, currentMb = 40.0f, totalMb = 80.0f)
}

@Preview(heightDp = 300)
@Composable
fun PreviewLoading() {
    Loading()
}

@Preview(heightDp = 300)
@Composable
fun PreviewLoading2() {
    Loading(LoadingType.WAIT)
}

@Composable
fun LoadingPopup(
    loadingType: LoadingType = LoadingType.LOADING,
    currentMb: Float? = null,
    totalMb: Float? = null,
    progress: Float? = null,
    backgroundColor: Color = Color(0x33000000),
    message: String? = null,
    optionComposable: @Composable (() -> Unit)? = null,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(enabled = false) { }
        .background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.clickable(enabled = false) { },
            backgroundColor = MaterialTheme.colors.background1Color
        ) {
            Column(
                modifier = Modifier.defaultMinSize(250.dp, 150.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LoadingImage(loadingType = loadingType)

                message?.let {
                    Text(
                        text = message,
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.subtitle1
                    )
                }

                if (progress != null && currentMb != null && totalMb != null) {
                    LinearProgressIndicator(
                        progress = progress, modifier = Modifier
                            .height(8.dp)
                            .width(dimensionResource(id = loadingType.minSize.first))
                    )
                    Text(
                        text = String.format("진행 중 ( %.1fMB / %.1fMB )", currentMb, totalMb),
                        style = MaterialTheme.typography.subtitle1
                    )
                } else if(progress != null) {
                    LinearProgressIndicator(
                        progress = progress, modifier = Modifier
                            .height(8.dp)
                            .width(dimensionResource(id = loadingType.minSize.first))
                    )
                }
                optionComposable?.invoke()
            }
        }
    }

}

@Composable
fun Loading(
    loadingType: LoadingType = LoadingType.LOADING,
    backgroundColor: Color = Color(0x33000000)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = false) {}
            .background(color = backgroundColor)
    ) {
        LoadingImage(loadingType = loadingType, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun LoadingImage(
    loadingType: LoadingType = LoadingType.LOADING,
    modifier: Modifier = Modifier
) {
    val gifListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return true
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            if (resource is GifDrawable) {
                resource.start()
            }
            return true
        }
    }

    GlideImage(
        imageModel = loadingType.resId,
        modifier = Modifier
            .size(
                dimensionResource(id = loadingType.minSize.first),
                dimensionResource(id = loadingType.minSize.second)
            )
            .then(modifier),
        requestBuilder = { Glide.with(LocalContext.current).asDrawable() },
        requestListener = gifListener,
        previewPlaceholder = loadingType.resId
    )
}

enum class LoadingType(@DrawableRes val resId: Int, val minSize: Pair<Int, Int>) {
    LOADING(R.drawable.loading, R.dimen.loading_img_width to R.dimen.loading_img_height),
    WAIT(R.drawable.tempimage, R.dimen.wait_img_width to R.dimen.wait_img_height)
}