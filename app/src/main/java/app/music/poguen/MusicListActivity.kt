package app.music.poguen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import app.music.poguen.databinding.ActivityMusicListBinding
import java.io.ByteArrayOutputStream

class MusicListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicListBinding

    private val musicList = ArrayList<AdapterModel.ListItem>()
    private val musicAdapter by lazy {MusicListAdapter(this, musicList)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_list)
        window.setBackgroundDrawableResource(R.drawable.main_bg)

        binding.mListRv.adapter = musicAdapter

        val titleArray = intent.extras?.getStringArrayList("titleList")
        val singerArray = intent.extras?.getStringArrayList("singerList")
        val imgArray = intent.extras?.getStringArrayList("imgList")

        repeat(titleArray?.size ?: 0) {
            addMusicList(
                titleArray?.get(it) ?: "", singerArray?.get(it) ?: "",
                getImage(sendImage(imgArray?.get(it)?.toInt() ?: 0)))

            musicAdapter.notifyItemInserted(it)
        }
    }

    private fun addMusicList(title: String, singer: String, img: Drawable) {
        val item = AdapterModel.ListItem(title, singer, img)

        musicList.add(item)
    }


    private fun sendImage(image: Int) : ByteArray {
        val sendBitmap = BitmapFactory.decodeResource(resources, image)
        val stream = ByteArrayOutputStream()
        sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        return stream.toByteArray()
    }

    private fun getImage(arr: ByteArray) : Drawable {
        return BitmapFactory.decodeByteArray(arr ,0, arr.size).toDrawable(resources)
    }
}