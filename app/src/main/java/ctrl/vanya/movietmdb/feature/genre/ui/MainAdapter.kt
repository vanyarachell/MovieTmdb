package ctrl.vanya.movietmdb.feature.genre.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ctrl.vanya.movietmdb.R
import ctrl.vanya.movietmdb.core.model.MasterMdl
import ctrl.vanya.movietmdb.feature.movie_list.ui.MovieListActivity
import kotlin.collections.ArrayList

class MainAdapter : ListAdapter<MainAdapter.DataItem, RecyclerView.ViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_genre,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val item = getItem(position) as DataItem.RepoList
            holder.bind(holder, item.item)

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, MovieListActivity::class.java)
                intent.putExtra(MovieListActivity.EXTRA_GENRE, item.item.id.toString())
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun submitList(list: List<DataItem>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    fun addDataAndSubmit(list: List<MasterMdl>) {
        val items: List<DataItem>? = if (list.size != 1) {
            when (itemCount) {
                list.size -> list.map {
                    DataItem.RepoList(
                        it
                    )
                }
                else -> list.map {
                    DataItem.RepoList(
                        it
                    )
                }
            }
        } else {
            list.map {
                DataItem.RepoList(
                    it
                )
            }
        }

        submitList(items)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val genre: TextView by lazy { view.findViewById<TextView>(R.id.tv_genre) }

        @SuppressLint("SetTextI18n")
        fun bind(
            holder: ViewHolder,
            item: MasterMdl
        ) {

            holder.genre.text = item.name
        }
    }

    sealed class DataItem {
        data class RepoList(val item: MasterMdl) : DataItem() {
            override val repoName = item.name!!
        }

        abstract val repoName: String
    }

    class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(
            oldItem: DataItem, newItem: DataItem
        ): Boolean {
            return oldItem.repoName == newItem.repoName
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: DataItem, newItem: DataItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}