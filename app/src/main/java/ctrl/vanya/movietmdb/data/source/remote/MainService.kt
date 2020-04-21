package ctrl.vanya.movietmdb.data.source.remote

import ctrl.vanya.movietmdb.core.model.GenreListMdl
import retrofit2.Response
import retrofit2.http.GET

interface MainService {
    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenreListMdl>
}