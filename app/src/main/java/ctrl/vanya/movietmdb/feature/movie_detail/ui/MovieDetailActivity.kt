package ctrl.vanya.movietmdb.feature.movie_detail.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ctrl.vanya.movietmdb.R
import ctrl.vanya.movietmdb.core.base.BaseViewState
import ctrl.vanya.movietmdb.core.di.CoreModule
import ctrl.vanya.movietmdb.core.helper.Constants
import ctrl.vanya.movietmdb.core.model.CastMdl
import ctrl.vanya.movietmdb.core.model.ReviewResultMdl
import ctrl.vanya.movietmdb.core.model.VideoResultMdl
import ctrl.vanya.movietmdb.core.utils.OnLoadMoreListener
import ctrl.vanya.movietmdb.feature.movie_detail.di.DaggerMovieDetailComponent
import ctrl.vanya.movietmdb.feature.movie_detail.di.MovieDetailModule
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.item_load_more.*
import kotlinx.android.synthetic.main.partial_details_info.*
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private var movieId: String? = null
    private lateinit var castAdapter: CastAdapter
    private lateinit var trailerAdapter: TrailerAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private var currentPage = 1
    private var totalPage = 1

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel: MovieDetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        injectDI()
        initObserve()
        setupUI()

        mViewModel.getMovieDetail(movieId!!)
    }

    private fun injectDI() {
        DaggerMovieDetailComponent.builder().movieDetailModule(MovieDetailModule())
            .coreModule(CoreModule(this))
            .build().inject(this)
    }

    @SuppressLint("SetTextI18n")
    private fun initObserve() {
        mViewModel.apply {
            movieDetailResult.observe(this@MovieDetailActivity, Observer {
                when (it) {
                    is BaseViewState.Loading -> {
                        showLoading()
                    }
                    is BaseViewState.Success -> {
                        stopLoading()

                        Glide.with(this@MovieDetailActivity)
                            .load(Constants.IMAGE_URL+it.data!!.backdropPath)
                            .into(image_movie_backdrop)

                        Glide.with(this@MovieDetailActivity)
                            .load(Constants.IMAGE_URL+it.data.posterPath)
                            .into(image_poster)

                        text_title.text = it.data.title
                        text_vote.text = it.data.voteAverage.toString()
                        text_language.text = it.data.originalLanguage
                        text_release_date.text = it.data.releaseDate
                        label_vote.text = it.data.voteCount.toString() + getString(R.string.label_votes)
                        text_overview.text = it.data.overview

                        setupCastAdapter(it.data.credits!!.cast)
                        setupTrailersAdapter(it.data.videos!!.results)
                        setupReviewsAdapter(it.data.reviews!!.results)

                        currentPage = it.data.reviews!!.page!!
                        totalPage = it.data.reviews!!.totalPages!!
                    }
                    is BaseViewState.Error -> {
                        stopLoading()
                        if (it.errorMessage!!.contains("connection")){
                            Log.e("error",it.errorMessage)
                        }
                    }
                }
            })
        }
    }

    private fun setupCastAdapter(cast: List<CastMdl>?) {
        val linearLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false
        )

        castAdapter = CastAdapter()
        castAdapter.addDataAndSubmit(cast!!)

        list_cast.apply {
            layoutManager = linearLayoutManager
            adapter = castAdapter
        }
    }

    private fun setupTrailersAdapter(results: List<VideoResultMdl>?) {
        val linearLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false
        )

        trailerAdapter = TrailerAdapter()
        trailerAdapter.addDataAndSubmit(results!!)

        list_trailers.apply {
            layoutManager = linearLayoutManager
            adapter = trailerAdapter
        }
    }

    private fun setupReviewsAdapter(results: List<ReviewResultMdl>?) {
        reviewAdapter = ReviewAdapter(this)
        reviewAdapter.setData(results!!)

        reviewAdapter.setLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                list_reviews.post {
                    currentPage++
                }
            }
        })
        list_reviews.apply {
            layoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                RecyclerView.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = reviewAdapter
        }
    }

    private fun showLoading() {
        network_state.isVisible = true
    }

    private fun stopLoading() {
        network_state.isVisible = false
    }

    private fun setupUI(){//setup toolbar
        setToolbar(getString(R.string.movie_detail))
        movieId = intent.extras?.getString(EXTRA_ID)
    }

    private fun setToolbar(title: String) {
        val toolbar = supportActionBar
        if (toolbar != null) {
            toolbar.title = title
            toolbar.elevation = 0f
            toolbar.setDisplayHomeAsUpEnabled(true)
            toolbar.setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        } else {
            Log.e("error","Toolbar is not set")
        }
    }
}
