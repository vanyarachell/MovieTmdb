package ctrl.vanya.movietmdb.feature.movie_list.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ctrl.vanya.movietmdb.core.base.BaseViewState
import ctrl.vanya.movietmdb.core.model.MovieListMdl
import ctrl.vanya.movietmdb.core.utils.AppDispatchers
import ctrl.vanya.movietmdb.data.repository.MovieListRepository
import ctrl.vanya.movietmdb.data.utils.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val repository: MovieListRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private val _mMovieListResult = MutableLiveData<BaseViewState<MovieListMdl>>()

    val movieListResult: LiveData<BaseViewState<MovieListMdl>> = _mMovieListResult

    private var jobCallApi: Job? = null

    var mForceRefresh = false

    private var mPage = 1

    override fun onCleared() {
        super.onCleared()
        jobCallApi?.cancel()
    }

    fun getMovieList(genreId : String, page : Int) {
        _mMovieListResult.value = BaseViewState.Loading
        jobCallApi?.cancel()
        jobCallApi = viewModelScope.launch {
            val request = withContext(dispatchers.io) {
                repository.getMovieList(genreId, page)
            }
            when (request) {
                is ResultState.Success -> {
                    _mMovieListResult.value = BaseViewState.Success(request.data)
                }
                is ResultState.Error -> {
                    _mMovieListResult.value = BaseViewState.Error(request.errorMessage)
                }
            }
        }
    }
}