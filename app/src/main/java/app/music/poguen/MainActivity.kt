package app.music.poguen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: app.music.poguen.databinding.ActivityMainBinding

    companion object {
        const val CM = "MY_PLAY_LIST"
        const val CR = "RECOMMENDED"
        const val CF = "FAVOURITE_ALBUM"
    }


    private val myPlayListItem = ArrayList<AdapterModel.MusicItem>()
    private val cmItem = ArrayList<AdapterModel.MusicItem>()
    private val crItem = ArrayList<AdapterModel.MusicItem>()
    private val cfItem = ArrayList<AdapterModel.MusicItem>()
    private val myPlayListAdapter by lazy {MusicAdapter(this,cmItem)}
    private val recommendedAdapter by lazy {MusicAdapter(this,crItem)}
    private val favouriteAlbumAdapter by lazy {MusicAdapter(this,cfItem)}

    private val musicArray = arrayOf(R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,
    R.drawable.a5,R.drawable.a6,R.drawable.a7,R.drawable.a8)

    private var count = 0

    private var p = 0.0f

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        window.setBackgroundDrawableResource(R.drawable.main_bg)

        binding.mainMyPlaylistRv.adapter = myPlayListAdapter
        binding.mainRecommendRv.adapter = recommendedAdapter
        binding.mainFavouriteRv.adapter = favouriteAlbumAdapter


        addMusicItem("밤공기","조현우",R.drawable.a1,listOf(CM,CR))
        addMusicItem("밤공기","조현우",R.drawable.a2,listOf(CM))
        addMusicItem("너를위한","suhwan",R.drawable.a3,listOf(CM,CR))
        addMusicItem("Loud Magazine","라우드매거진",R.drawable.a4,listOf(CM,CR,CF))
        addMusicItem("Thumbs Up","칸바아트홀",R.drawable.a5,listOf(CM,CF))
        addMusicItem("Crawl City","David Marson",R.drawable.a6,listOf(CM,CR))
        addMusicItem("Montage","Louise",R.drawable.a7,listOf(CM))
        addMusicItem("사랑","김철수",R.drawable.a8,listOf(CM,CR))

        myPlayListItem.forEach {
            if (it.category.contains(CM)) {
                cmItem.add(it)
            }
            if (it.category.contains(CR)) {
                crItem.add(it)
            }
            if (it.category.contains(CF)) {
                cfItem.add(it)
            }
        }

        myPlayListAdapter.notifyDataSetChanged()
        recommendedAdapter.notifyDataSetChanged()
        favouriteAlbumAdapter.notifyDataSetChanged()

        binding.mainBottomPlaying.apply{
            setImageDrawable(res(musicArray[count]))
            setProgressAnimationState(true)
            setValue(p)
        }

        changeProgress()

        binding.mainBottomPlayingCard.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayerActivity::class.java)
            intent.putExtra("title","We Need Our Army Back")
            intent.putExtra("singer","Haber Zomitor")
            intent.putExtra("img", sendImage(R.drawable.a1))
            this.startActivity(intent)
        }

        binding.mainMyPlaylistLayout.setOnClickListener {
            intentListActivity(cmItem)
        }

        binding.mainRecommendLayout.setOnClickListener {
            intentListActivity(crItem)
        }

        binding.mainFavouriteLayout.setOnClickListener {
            intentListActivity(cfItem)
        }
    }

    private fun changeProgress() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (p + 3 >= 100.0f) {
                p = 0f
                count++
                binding.mainBottomPlaying.setValue(0f)
                binding.mainBottomPlaying.setImageDrawable(res(musicArray[count]))
            } else {
                p += 5
                binding.mainBottomPlaying.setValue(p)
            }
            changeProgress()
        },1000)
    }

    private fun res(i: Int): Drawable? {
        return ResourcesCompat.getDrawable(resources, i,null)
    }

    fun getDrawableFromResource(@DrawableRes drawableResId: Int): Drawable? {
        return AppCompatResources.getDrawable(this, drawableResId)
    }

    fun compareDrawables(drawable1: Drawable?, drawable2: Drawable?): Boolean {
        return drawable1?.constantState == drawable2?.constantState
    }

    private fun intentListActivity(list: ArrayList<AdapterModel.MusicItem>) {
        val intent = Intent(this, MusicListActivity::class.java)
        val titleArray = ArrayList<String>()
        val singerArray = ArrayList<String>()
        val imgArray = ArrayList<String>()
        list.forEach {
            titleArray.add(it.title)
            singerArray.add(it.singer)
            imgArray.add(it.img.toString())
        }
        intent.putExtra("titleList",titleArray)
        intent.putExtra("singerList",singerArray)
        intent.putExtra("imgList",imgArray)
        startActivity(intent)
    }

    private fun addMusicItem(title: String, singer: String, img: Int, category: List<String>) {
        val item = AdapterModel.MusicItem(title, singer, img, category)

        myPlayListItem.add(item)
    }

    private fun sendImage(image: Int) : ByteArray {
        val sendBitmap = BitmapFactory.decodeResource(resources, image)
        val stream = ByteArrayOutputStream()
        sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        return stream.toByteArray()
    }
}