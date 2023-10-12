package app.music.poguen

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import app.music.poguen.databinding.ActivityPlayerBinding
import at.favre.lib.dali.Dali
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding

    private var isTransition = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_player)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        val title = intent.extras?.getString("title") ?: ""
        val singer = intent.extras?.getString("singer") ?: ""

        binding.playerTitle.text = title
        binding.playerSinger.text = singer

        Dali.create(this).load(getImage().toBitmap()).downScale(16).skipCache().brightness(-25f)
           .blurRadius(5).into(binding.playerBackground)

        Glide.with(this)
            .load(getImage())
            .override(SIZE_ORIGINAL)
            .dontTransform()
            .into(binding.playerCover)

        binding.playerParent.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout, startId: Int, endId: Int) {}

            override fun onTransitionChange(
                motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float) {}

            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                if (currentId == motionLayout.endState) {
                    isTransition = true
                } else if (currentId == motionLayout.startState) {
                    isTransition = false
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout, triggerId: Int, positive: Boolean, progress: Float) {}}
        )

        binding.playerCard.setOnClickListener {
            if(isTransition) {
                binding.playerParent.transitionToStart()
            } else {
                binding.playerParent.transitionToEnd()
            }
        }

        binding.playerPrev.setOnClickListener {
            if(isTransition) {
                binding.playerParent.transitionToStart()
            } else {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if(isTransition) {
            binding.playerParent.transitionToStart()
        } else {
            finish()
        }
    }

    private fun res(i: Int): Drawable? {
        return ResourcesCompat.getDrawable(resources, i,null)
    }

    private fun getImage() : Drawable {
        val arr = intent.getByteArrayExtra("img")
        return BitmapFactory.decodeByteArray(arr, 0, arr!!.size).toDrawable(resources)
    }
}