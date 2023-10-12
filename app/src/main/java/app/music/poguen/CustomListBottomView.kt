package app.music.poguen

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import app.music.poguen.databinding.CustomImgTextBinding

class CustomListBottomView(context: Context, attrs: AttributeSet?)
    : LinearLayout(context, attrs) {
    private var customerBinding: CustomImgTextBinding

    init {
        val inflater = LayoutInflater.from(context)
        customerBinding = CustomImgTextBinding.inflate(inflater, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomListBottomView)
            val src = typedArray.getInt(R.styleable.CustomListBottomView_customSrc,0)
            val title = typedArray.getString(R.styleable.CustomListBottomView_customTitle)
            typedArray.recycle()

            customerBinding.mListBottomPlayText.text = title
            customerBinding.mListBottomPlayImg.setImageDrawable(when(src) {
                1 -> ResourcesCompat.getDrawable(resources,R.drawable.play,null)
                2 -> ResourcesCompat.getDrawable(resources,R.drawable.inbox,null)
                3 -> ResourcesCompat.getDrawable(resources,R.drawable.music_list,null)
                4 -> ResourcesCompat.getDrawable(resources,R.drawable.download,null)
                else -> ResourcesCompat.getDrawable(resources,R.drawable.music,null)
            })
        }
    }
}