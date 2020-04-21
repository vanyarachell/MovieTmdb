package ctrl.vanya.movietmdb.feature.genre.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ctrl.vanya.movietmdb.core.base.BaseViewState
import ctrl.vanya.movietmdb.core.model.GenreListMdl
import ctrl.vanya.movietmdb.core.utils.AppDispatchers
import ctrl.vanya.movietmdb.data.repository.MainRepository
import ctrl.vanya.movietmdb.data.utils.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private val _mGenreListResult = MutableLiveData<BaseViewState<GenreListMdl>>()

    val genreListResult: LiveData<BaseViewState<GenreListMdl>> = _mGenreListResult

    private var jobCallApi: Job? = null

    var mForceRefresh = false

    private var mPage = 1

    override fun onCleared() {
        super.onCleared()
        jobCallApi?.cancel()
    }

    fun getGenreList() {
        _mGenreListResult.value = BaseViewState.Loading
        jobCallApi?.cancel()
        jobCallApi = viewModelScope.launch {
            val request = withContext(dispatchers.io) {
                repository.getGenreList()
            }
            when (request) {
                is ResultState.Success -> {
                    _mGenreListResult.value = BaseViewState.Success(request.data)
                }
                is ResultState.Error -> {
                    _mGenreListResult.value = BaseViewState.Error(request.errorMessage)
                }
            }
        }
    }
}