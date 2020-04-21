package ctrl.vanya.movietmdb.feature.movie_detail.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import ctrl.vanya.movietmdb.R
import ctrl.vanya.movietmdb.core.model.ReviewResultMdl
import ctrl.vanya.movietmdb.core.utils.OnLoadMoreListener

class ReviewAdapter (private val activity: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listMovie = ArrayList<ReviewResultMdl>()

    private val typeProgress = 0
    private val typeLinear = 1

    private var isLoading = false
    private var isMoreDataAvailable = true

    private var loadMoreListener: OnLoadMoreListener? = null

    fun setData(items: List<ReviewResultMdl>) {
        if (listMovie.isNotEmpty()) {
            listMovie.add(ReviewResultMdl("load"))
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
            MovieViewHolder(inflater.inflate(R.layout.item_review, parent, false))
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
        private val imgAuthor: ImageView = itemView.findViewById(R.id.image_author)
        private val tvAuthorName: TextView = itemView.findViewById(R.id.text_author)
        private val tvAuthorReview: TextView = itemView.findViewById(R.id.text_content)


        fun bind(movie: ReviewResultMdl) {
            if (movie.author != null) {

                tvAuthorName.text = movie.author
                tvAuthorReview.text = movie.content

                // review user image
                val generator: ColorGenerator = ColorGenerator.MATERIAL
                val color: Int = generator.randomColor
                val drawable: TextDrawable = TextDrawable.builder()
                    .buildRound(movie.author!!.substring(0, 1).toUpperCase(), color)
                imgAuthor.setImageDrawable(drawable)
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