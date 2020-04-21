package ctrl.vanya.movietmdb.data.source.remote

import ctrl.vanya.movietmdb.core.model.GenreListMdl
import ctrl.vanya.movietmdb.data.source.MainDataSource
import ctrl.vanya.movietmdb.data.utils.ResultState
import ctrl.vanya.movietmdb.data.utils.fetchState
import ctrl.vanya.movietmdb.data.utils.handleError
import javax.inject.Inject

class RemoteMainDataSource @Inject constructor(
    private val mainService: MainService
) : MainDataSource {
    override suspend fun getGenreList(): ResultState<GenreListMdl> {
        return fetchState {
            val response = mainService.getGenres()
            val data: GenreListMdl
            if (response.isSuccessful) {
                data = response.body()!!
                ResultState.Success(data)
            } else {
                ResultState.Error(response.handleError().message)
            }
        }
    }
}