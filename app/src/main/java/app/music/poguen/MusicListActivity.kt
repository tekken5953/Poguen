package app.music.poguen

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import app.music.poguen.DataTypeParser.getImage
import app.music.poguen.DataTypeParser.sendImage
import app.music.poguen.databinding.ActivityMusicListBinding

class MusicListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicListBinding

    private val musicList = ArrayList<AdapterModel.ListItem>()
    private val musicAdapter by lazy {MusicListAdapter(this, musicList)}

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_list)
        window.setBackgroundDrawableResource(R.drawable.main_bg)

        binding.mListRv.adapter = musicAdapter

        binding.mListTitle.text = intent.extras?.getString("subject")

        val titleArray = intent.extras?.getStringArrayList("titleList")
        val singerArray = intent.extras?.getStringArrayList("singerList")
        val imgArray = intent.extras?.getIntegerArrayList("imgList")

        repeat(titleArray?.size ?: 0) {
            addMusicList(
                titleArray?.get(it) ?: "", singerArray?.get(it) ?: "",
                getImage(this, sendImage(this, imgArray?.get(it) ?: 0)))

            musicAdapter.notifyItemInserted(it)
        }


        musicAdapter.setOnItemClickListener(object : MusicListAdapter.OnItemClickListener{
            override fun onItemClick(v: View, position: Int) {
                if (!musicAdapter.getClicked(position)) {
                    count++
                    musicAdapter.changeClicked(position,true)
                    v.setBackgroundColor(getColor(R.color.checked_color))
                    binding.mListBottomMenu.visibility = View.VISIBLE
                } else {
                    musicAdapter.changeClicked(position,false)
                    v.setBackgroundColor(getColor(android.R.color.transparent))
                    if (--count == 0) {
                        binding.mListBottomMenu.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun addMusicList(title: String, singer: String, img: Drawable) {
        val item = AdapterModel.ListItem(title, singer, img, false)

        musicList.add(item)
    }
}