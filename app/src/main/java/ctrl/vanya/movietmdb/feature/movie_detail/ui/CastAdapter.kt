package ctrl.vanya.movietmdb.feature.movie_detail.ui

import android.annotation.SuppressLint
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
import ctrl.vanya.movietmdb.core.model.CastMdl

class CastAdapter : ListAdapter<CastAdapter.DataItem, RecyclerView.ViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_cast,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val item = getItem(position) as DataItem.CastList
            holder.bind(holder, item.item)

        }
    }

    override fun submitList(list: List<DataItem>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    fun addDataAndSubmit(list: List<CastMdl>) {
        val items: List<DataItem>? = if (list.size != 1) {
            when (itemCount) {
                list.size -> list.map {
                    DataItem.CastList(
                        it
                    )
                }
                else -> list.map {
                    DataItem.CastList(
                        it
                    )
                }
            }
        } else {
            list.map {
                DataItem.CastList(
                    it
                )
            }
        }

        submitList(items)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val castName: TextView by lazy { view.findViewById<TextView>(R.id.text_cast_name) }
        private val castImage: ImageView by lazy { view.findViewById<ImageView>(R.id.image_cast_profile) }

        @SuppressLint("SetTextI18n")
        fun bind(
            holder: ViewHolder,
            item: CastMdl
        ) {
            val profileImage: String =
                Constants.IMAGE_BASE_URL + Constants.PROFILE_SIZE_W185 + item.profilePath
            Glide.with(itemView.context)
                .load(profileImage)
                .placeholder(R.color.colorPrimary)
                .dontAnimate()
                .into(holder.castImage)

            holder.castName.text = item.name
        }
    }

    sealed class DataItem {
        data class CastList(val item: CastMdl) : DataItem() {
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