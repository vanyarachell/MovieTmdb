package ctrl.vanya.movietmdb.feature.movie_list.ui

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ctrl.vanya.movietmdb.R
import ctrl.vanya.movietmdb.core.helper.Constants
import ctrl.vanya.movietmdb.core.model.ResultMdl
import ctrl.vanya.movietmdb.core.utils.OnLoadMoreListener
import ctrl.vanya.movietmdb.feature.movie_detail.ui.MovieDetailActivity

class MovieListAdapter (private val activity: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listMovie = ArrayList<ResultMdl>()

    private val typeProgress = 0
    private val typeLinear = 1

    private var isLoading = false
    private var isMoreDataAvailable = true

    private var loadMoreListener: OnLoadMoreListener? = null

    fun setData(items: List<ResultMdl>) {
        if (listMovie.isNotEmpty()) {
            listMovie.add(ResultMdl("load"))
            this.notifyItemInserted(listMovie.size - 1)
            if (items.isNotEmpty()) {
                listMovie.removeAt(listMovie.size - 1)
                listMovie.addAll(items)
            } else {
                this.setMoreDataAvailable(false)
            }
        } else {
            listMovie.addAll(items)
        }
        notifyDataSetChanged()
        isLoading = false
    }

    fun clearList() {
        listMovie.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(activity)
        return if (viewType == typeLinear) {
            MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
        } else {
            LoadHolder(inflater.inflate(R.layout.item_load_more, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= itemCount - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true
            loadMoreListener!!.onLoadMore()
        }

        if (getItemViewType(position) == typeLinear) {
            (holder as MovieViewHolder)
            holder.bind(listMovie[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listMovie[position].loading == "load") {
            typeProgress
        } else {
            typeLinear
        }
    }

    override fun getItemCount() = listMovie.size

    class MovieViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val imgPhoto: ImageView = itemView.findViewById(R.id.image_movie_poster)
        private val tvTitle: TextView = itemView.findViewById(R.id.text_title)


        fun bind(movie: ResultMdl) {
            if (movie.title != null) {
                tvTitle.text = movie.title
                Glide.with(itemView.context)
                    .load(Constants.IMAGE_URL + movie.posterPath)
                    .into(imgPhoto)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                    intent.putExtra(MovieDetailActivity.EXTRA_ID, movie.id.toString())
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    private class LoadHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    fun setMoreDataAvailable(moreDataAvailable: Boolean) {
        isMoreDataAvailable = moreDataAvailable
    }

    fun setLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }
}