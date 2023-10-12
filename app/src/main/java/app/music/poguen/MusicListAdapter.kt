package app.music.poguen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MusicListAdapter(
    private val context: Activity,
    list: ArrayList<AdapterModel.ListItem>,
) :
    RecyclerView.Adapter<MusicListAdapter.ViewHolder>() {
    private val mList = list

    private lateinit var onClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onClickListener = listener
    }

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

//            itemView.setOnClickListener {
//                val position = adapterPosition
//
//                if (position != RecyclerView.NO_POSITION) {
//                    try {
//                        val intent = Intent(context, PlayerActivity::class.java)
//                        intent.putExtra("title", dao.title)
//                        intent.putExtra("singer", dao.singer)
//                        intent.putExtra("img", sendImage(context,dao.img.toString()))
//                        context.startActivity(intent)
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }
//            }
            itemView.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    onClickListener.onItemClick(it, position)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeClicked(p: Int, b: Boolean) {
        mList[p].clicked = b
        notifyDataSetChanged()
    }

    fun getClicked(p: Int): Boolean {
        return mList[p].clicked
    }
}