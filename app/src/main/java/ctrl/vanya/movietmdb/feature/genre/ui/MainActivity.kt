package ctrl.vanya.movietmdb.feature.genre.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ctrl.vanya.movietmdb.R
import ctrl.vanya.movietmdb.core.base.BaseViewState
import ctrl.vanya.movietmdb.core.di.CoreModule
import ctrl.vanya.movietmdb.core.model.MasterMdl
import ctrl.vanya.movietmdb.feature.genre.di.DaggerMainComponent
import ctrl.vanya.movietmdb.feature.genre.di.MainModule
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_load_more.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: MainAdapter
    private var list: ArrayList<MasterMdl>? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDI()
        initObserve()
        initRecyclerView()
        setupUI()

        mViewModel.getGenreList()
    }

    private fun injectDI() {
        DaggerMainComponent.builder().mainModule(MainModule())
            .coreModule(CoreModule(this))
            .build().inject(this)
    }

    private fun initObserve() {
        mViewModel.apply {
            genreListResult.observe(this@MainActivity, Observer {
                when (it) {
                    is BaseViewState.Loading -> {
                        if (mForceRefresh) {
                            showLoading()
                        }
                    }
                    is BaseViewState.Success -> {
                        stopLoading()

                        list = ArrayList()
                        list!!.addAll(it.data!!.genres!!)
                        it.data.let { it1 ->
                            mAdapter.addDataAndSubmit(it1.genres!!)
                        }
                    }
                    is BaseViewState.Error -> {
                        stopLoading()
                        if (it.errorMessage!!.contains("connection")){
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
        val linearLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        mAdapter = MainAdapter()

        rv_genre_list.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }
    }

    private fun setupUI(){
        //setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.choose_genre)
        setSupportActionBar(toolbar)
    }
}
