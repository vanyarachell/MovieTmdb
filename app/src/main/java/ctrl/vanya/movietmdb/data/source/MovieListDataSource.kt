package ctrl.vanya.movietmdb.data.source

import ctrl.vanya.movietmdb.core.model.MovieListMdl
import ctrl.vanya.movietmdb.data.utils.ResultState
import retrofit2.http.Query

interface MovieListDataSource {
    suspend fun getMovieList(
        @Query("with_genres") genreId: String,
        @Query("page") page: Int
    ): ResultState<MovieListMdl>
}