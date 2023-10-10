package app.music.poguen

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import app.music.poguen.databinding.ActivityPlayerBinding
import at.favre.lib.dali.Dali
import com.bumptech.glide.Glide


class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_player)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        val title = intent.extras?.getString("title") ?: ""
        val singer = intent.extras?.getString("singer") ?: ""

        binding.playerTitle.text = title
        binding.playerSinger.text = singer

        Dali.create(this).load(getImage().toBitmap()).downScale(16).skipCache()
           .blurRadius(5).into(binding.playerBackground)

        Glide.with(this).load(getImage()).into(binding.playerCover)

        binding.playerBack.setOnClickListener {
            finish()
        }
    }

    private fun getImage() : Drawable {
        val arr = intent.getByteArrayExtra("img")
        return BitmapFactory.decodeByteArray(arr, 0, arr!!.size).toDrawable(resources)
    }

}