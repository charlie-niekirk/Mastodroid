package me.cniekirk.mastodroid.core.ui

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import timber.log.Timber

suspend fun Context.loadImage(url: String, onLoaded: suspend (Bitmap) -> Unit) {
    val request = ImageRequest.Builder(this)
        .data(url)
        .build()
    when (val result = ImageLoader(this).execute(request)) {
        is ErrorResult -> {
            Timber.e(result.throwable)
        }
        is SuccessResult -> {
            onLoaded(result.drawable.toBitmap())
        }
    }
}