package ctrl.vanya.movietmdb.data.source.remote

import ctrl.vanya.movietmdb.core.model.MovieListMdl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListService {
    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("with_genres") genreId: String
    ): Response<MovieListMdl>
}