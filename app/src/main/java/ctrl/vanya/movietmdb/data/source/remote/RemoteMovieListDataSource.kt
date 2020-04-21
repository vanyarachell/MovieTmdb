package ctrl.vanya.movietmdb.data.source.remote

import ctrl.vanya.movietmdb.core.model.MovieListMdl
import ctrl.vanya.movietmdb.data.source.MovieListDataSource
import ctrl.vanya.movietmdb.data.utils.ResultState
import ctrl.vanya.movietmdb.data.utils.fetchState
import ctrl.vanya.movietmdb.data.utils.handleError
import javax.inject.Inject

class RemoteMovieListDataSource @Inject constructor(
    private val movieListService: MovieListService
) : MovieListDataSource {
    override suspend fun getMovieList(genreId: String, page: Int): ResultState<MovieListMdl> {
        return fetchState {
            val response = movieListService.getMovieList(genreId)
            val data: MovieListMdl
            if (response.isSuccessful) {
                data = response.body()!!
                ResultState.Success(data)
            } else {
                ResultState.Error(response.handleError().message)
            }
        }
    }
}