package app.music.poguen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayOutputStream

class MusicAdapter(
    private val context: Activity,
    list: ArrayList<AdapterModel.MusicItem>,
) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    private val mList = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.list_item_category_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.listItemCategoryTitle)
        private val singer = view.findViewById<TextView>(R.id.listItemCategorySinger)
        private val img = view.findViewById<ImageView>(R.id.listItemCategoryImg)

        fun bind(dao: AdapterModel.MusicItem) {
            title.text = dao.title
            singer.text = dao.singer
            img.setImageDrawable(ResourcesCompat.getDrawable(context.resources,dao.img,null))

            itemView.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    try {
                        val intent = Intent(context,PlayerActivity::class.java)
                        intent.putExtra("title",dao.title)
                        intent.putExtra("singer",dao.singer)
                        intent.putExtra("img",sendImage(dao.img))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }


    }

    fun sendImage(image: Int) : ByteArray {
        val sendBitmap = BitmapFactory.decodeResource(context.resources, image)
        val stream = ByteArrayOutputStream()
        sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }
}