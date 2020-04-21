package ctrl.vanya.movietmdb.feature.movie_detail.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ctrl.vanya.movietmdb.R
import ctrl.vanya.movietmdb.core.helper.Constants
import ctrl.vanya.movietmdb.core.model.VideoResultMdl

class TrailerAdapter : ListAdapter<TrailerAdapter.DataItem, RecyclerView.ViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_trailer,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val item = getItem(position) as DataItem.VideoList
            holder.bind(holder, item.item)

        }
    }

    override fun submitList(list: List<DataItem>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    fun addDataAndSubmit(list: List<VideoResultMdl>) {
        val items: List<DataItem>? = if (list.size != 1) {
            when (itemCount) {
                list.size -> list.map {
                    DataItem.VideoList(
                        it
                    )
                }
                else -> list.map {
                    DataItem.VideoList(
                        it
                    )
                }
            }
        } else {
            list.map {
                DataItem.VideoList(
                    it
                )
            }
        }

        submitList(items)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val trailerTitle: TextView by lazy { view.findViewById<TextView>(R.id.trailer_name) }
        private val trailerThumbnail: ImageView by lazy { view.findViewById<ImageView>(R.id.image_trailer) }

        @SuppressLint("SetTextI18n")
        fun bind(
            holder: ViewHolder,
            item: VideoResultMdl
        ) {

            holder.trailerTitle.text = item.name

            val thumbnail =
                "https://img.youtube.com/vi/" + item.key.toString() + "/hqdefault.jpg"
            Glide.with(itemView.context)
                .load(thumbnail)
                .placeholder(R.color.colorPrimary)
                .into(holder.trailerThumbnail)

            holder.itemView.setOnClickListener(View.OnClickListener {
                val appIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("vnd.youtube:" + item.key)
                )
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.YOUTUBE_WEB_URL + item.key)
                )
                if (appIntent.resolveActivity(itemView.context.packageManager) != null) {
                    itemView.context.startActivity(appIntent)
                } else {
                    itemView.context.startActivity(webIntent)
                }
            })
        }
    }

    sealed class DataItem {
        data class VideoList(val item: VideoResultMdl) : DataItem() {
            override val castName = item.name!!
        }

        abstract val castName: String
    }

    class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(
            oldItem: DataItem, newItem: DataItem
        ): Boolean {
            return oldItem.castName == newItem.castName
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: DataItem, newItem: DataItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}