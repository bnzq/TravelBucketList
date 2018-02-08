package com.b00sti.travelbucketlist.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat.startActivityForResult
import com.b00sti.travelbucketlist.utils.AppConstants.CODE_PICK_PHOTO
import java.io.File


/**
 * Created by b00sti on 05.01.2018
 */
object ImageLoader {
    private val EMPTY_METHOD: (Bitmap) -> Unit = {}

    fun onPickPhoto(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        if (intent.resolveActivity(activity.packageManager) != null) {
            startActivityForResult(activity, intent, CODE_PICK_PHOTO, null)
        }
    }

    fun getBitmap(activity: Activity, data: Intent?, listener: (Bitmap) -> Unit, maxWidth: Int = 240, maxHeight: Int = 240, quality: Int = 75) {
        val selectedImage: File?
        if (data != null) {
            val photoUri = data.data
            selectedImage = FileUtil.from(activity, photoUri)
            //compress(selectedImage, listener, maxWidth, maxHeight, quality)
        }
    }

/*    private fun compress(file: File, listener: (Bitmap) -> Unit = ImageLoader.EMPTY_METHOD,
                         maxWidth: Int = 240, maxHeight: Int = 240, quality: Int = 75) {
        Compressor(App.appCtx())
                .setMaxWidth(maxWidth)
                .setMaxHeight(maxHeight)
                .setQuality(quality)
                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).absolutePath)
                .compressToFileAsFlowable(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listener(BitmapFactory.decodeFile(it.absolutePath)) })
    }*/
}