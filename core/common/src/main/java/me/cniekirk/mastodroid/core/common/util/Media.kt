package me.cniekirk.mastodroid.core.common.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

fun Context.shareText(textToShare: String) {
    val share = Intent.createChooser(Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, textToShare)
    }, null)
    startActivity(share)
}

fun Context.shareMedia(mediaUri: Uri) {
    val share = Intent.createChooser(Intent().apply {
        action = Intent.ACTION_SEND
        type = contentResolver.getType(mediaUri)
        putExtra(Intent.EXTRA_STREAM, mediaUri)
    }, null)
    startActivity(share)
}

fun Context.shareMedia(mediaUris: ArrayList<Uri>) {
    val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
        type = "image/*"
        putParcelableArrayListExtra(Intent.EXTRA_STREAM, mediaUris)
    }
    startActivity(Intent.createChooser(intent, "Share Media"))
}

suspend fun Context.getUriFromBitmap(bmp: Bitmap): Uri =
    withContext(Dispatchers.IO) {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        var imageUri: Uri?
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/*")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Video.Media.IS_PENDING, 1)
        }

        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            imageUri?.let {
                resolver.openOutputStream(it)?.use { output ->
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, output)
                }
            }
        }

        contentValues.clear()
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
        contentResolver.update(imageUri!!, contentValues, null, null)

        Timber.d("HELLO: $imageUri")

        imageUri!!
    }
