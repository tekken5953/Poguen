package app.music.poguen

import android.graphics.drawable.Drawable

object AdapterModel {
    data class MusicItem(
        val title: String,
        val singer: String,
        val img: Int,
        val category: List<String>
    )

    data class ListItem(
        val title: String,
        val singer: String,
        val img: Drawable,
        var clicked: Boolean
    )
}