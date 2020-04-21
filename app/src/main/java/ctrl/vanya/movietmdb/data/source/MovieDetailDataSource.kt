package ctrl.vanya.movietmdb.data.source

import ctrl.vanya.movietmdb.core.model.MovieDetailMdl
import ctrl.vanya.movietmdb.data.utils.ResultState
import retrofit2.http.Path

interface MovieDetailDataSource {
    suspend fun getMovieDetail(@Path("movie_id") movie_id: String): ResultState<MovieDetailMdl>
}