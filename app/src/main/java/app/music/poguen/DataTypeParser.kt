package app.music.poguen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable
import java.io.ByteArrayOutputStream

object DataTypeParser {

    fun sendImage(context: Context, image: String) : ByteArray {
        val sendBitmap = BitmapFactory.decodeResource(context.resources, image.toInt())
        val stream = ByteArrayOutputStream()
        sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        return stream.toByteArray()
    }

    fun sendImage(context: Context, image: Int) : ByteArray {
        val sendBitmap = BitmapFactory.decodeResource(context.resources, image)
        val stream = ByteArrayOutputStream()
        sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        return stream.toByteArray()
    }

    fun getImage(context: Context, arr: ByteArray) : Drawable {
        return BitmapFactory.decodeByteArray(arr ,0, arr.size).toDrawable(context.resources)
    }
}