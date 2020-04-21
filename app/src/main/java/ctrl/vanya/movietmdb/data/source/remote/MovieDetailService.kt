package ctrl.vanya.movietmdb.data.source.remote

import ctrl.vanya.movietmdb.core.model.MovieDetailMdl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailService {
    @GET("movie/{movie_id}?append_to_response=videos,credits,reviews")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: String
    ): Response<MovieDetailMdl>
}