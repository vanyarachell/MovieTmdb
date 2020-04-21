package ctrl.vanya.movietmdb.feature.movie_list.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import ctrl.vanya.movietmdb.R
import ctrl.vanya.movietmdb.core.base.BaseViewState
import ctrl.vanya.movietmdb.core.di.CoreModule
import ctrl.vanya.movietmdb.core.utils.OnLoadMoreListener
import ctrl.vanya.movietmdb.feature.movie_list.di.DaggerMovieListComponent
import ctrl.vanya.movietmdb.feature.movie_list.di.MovieListModule
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.item_load_more.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MovieListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_GENRE = "extra_genre"
    }

    private lateinit var mAdapter: MovieListAdapter
    private var currentPage = 1
    private var genreId: String? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel: MovieListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        injectDI()
        initObserve()
        initRecyclerView()
        setupUI()

        mViewModel.getMovieList(genreId!!, 1)
    }

    private fun injectDI() {
        DaggerMovieListComponent.builder().movieListModule(MovieListModule())
            .coreModule(CoreModule(this))
            .build().inject(this)
    }

    private fun initObserve() {
        mViewModel.apply {
            movieListResult.observe(this@MovieListActivity, Observer {
                when (it) {
                    is BaseViewState.Loading -> {
                        showLoading()
                    }
                    is BaseViewState.Success -> {
                        stopLoading()
                        mAdapter.setData(it.data!!.results!!)
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

    private fun showLoading() {
        progress_dialog.isVisible = true
    }

    private fun stopLoading() {
        progress_dialog.isVisible = false
    }

    private fun initRecyclerView() {
        mAdapter = MovieListAdapter(this)
        mAdapter.setLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                rv_movie_list.post {
                    currentPage++
                    progress_dialog.visibility = View.VISIBLE
                    mViewModel.getMovieList(genreId!!, currentPage)
                }
            }
        })
        rv_movie_list.apply {
            layoutManager = GridLayoutManager(this@MovieListActivity, 3)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun setupUI(){//setup toolbar
        setToolbar(getString(R.string.movie_list))
        genreId = intent.extras?.getString(EXTRA_GENRE)
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
