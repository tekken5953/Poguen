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
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

class MusicListAdapter(
    private val context: Activity,
    list: ArrayList<AdapterModel.ListItem>,
) :
    RecyclerView.Adapter<MusicListAdapter.ViewHolder>() {
    private val mList = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicListAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.list_item_music_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.listItemMListTitle)
        private val singer = view.findViewById<TextView>(R.id.listItemMListSinger)
        private val img = view.findViewById<ImageView>(R.id.listItemMListImg)

        fun bind(dao: AdapterModel.ListItem) {
            title.text = dao.title
            singer.text = dao.singer
            Glide.with(context).load(dao.img).into(img)

            itemView.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    try {
                        val intent = Intent(context, PlayerActivity::class.java)
                        intent.putExtra("title", dao.title)
                        intent.putExtra("singer", dao.singer)
                        intent.putExtra("img", sendImage(dao.img.toString()))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun sendImage(image: String) : ByteArray {
        val sendBitmap = BitmapFactory.decodeResource(context.resources, image.toInt())
        val stream = ByteArrayOutputStream()
        sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }
}