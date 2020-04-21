package ctrl.vanya.movietmdb.feature.movie_detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ctrl.vanya.movietmdb.core.base.BaseViewState
import ctrl.vanya.movietmdb.core.model.MovieDetailMdl
import ctrl.vanya.movietmdb.core.utils.AppDispatchers
import ctrl.vanya.movietmdb.data.repository.MovieDetailRepository
import ctrl.vanya.movietmdb.data.utils.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private val _mMovieDetailResult = MutableLiveData<BaseViewState<MovieDetailMdl>>()

    val movieDetailResult: LiveData<BaseViewState<MovieDetailMdl>> = _mMovieDetailResult

    private var jobCallApi: Job? = null

    var mForceRefresh = false

    private var mPage = 1

    override fun onCleared() {
        super.onCleared()
        jobCallApi?.cancel()
    }

    fun getMovieDetail(movieId : String) {
        _mMovieDetailResult.value = BaseViewState.Loading
        jobCallApi?.cancel()
        jobCallApi = viewModelScope.launch {
            val request = withContext(dispatchers.io) {
                repository.getMovieDetail(movieId)
            }
            when (request) {
                is ResultState.Success -> {
                    _mMovieDetailResult.value = BaseViewState.Success(request.data)
                }
                is ResultState.Error -> {
                    _mMovieDetailResult.value = BaseViewState.Error(request.errorMessage)
                }
            }
        }
    }
}