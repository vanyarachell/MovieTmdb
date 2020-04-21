package ctrl.vanya.movietmdb.data.source.remote

import ctrl.vanya.movietmdb.core.model.MovieDetailMdl
import ctrl.vanya.movietmdb.data.source.MovieDetailDataSource
import ctrl.vanya.movietmdb.data.utils.ResultState
import ctrl.vanya.movietmdb.data.utils.fetchState
import ctrl.vanya.movietmdb.data.utils.handleError
import javax.inject.Inject

class RemoteMovieDetailDataSource @Inject constructor(
    private val movieDetailService: MovieDetailService
) : MovieDetailDataSource {
    override suspend fun getMovieDetail(movieId: String): ResultState<MovieDetailMdl> {
        return fetchState {
            val response = movieDetailService.getMovieDetail(movieId)
            val data: MovieDetailMdl
            if (response.isSuccessful) {
                data = response.body()!!
                ResultState.Success(data)
            } else {
                ResultState.Error(response.handleError().message)
            }
        }
    }
}