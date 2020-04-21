package ctrl.vanya.movietmdb.data.repository

import ctrl.vanya.movietmdb.core.model.MovieListMdl
import ctrl.vanya.movietmdb.data.source.MovieListDataSource
import ctrl.vanya.movietmdb.data.utils.ResultState
import javax.inject.Inject

interface MovieListRepository {
    suspend fun getMovieList(genreId: String, page: Int): ResultState<MovieListMdl>

    class MovieListRepositoryImpl @Inject constructor(private val remoteMovieListDataSource: MovieListDataSource) : MovieListRepository {
        override suspend fun getMovieList(genreId: String, page: Int): ResultState<MovieListMdl> {
            return remoteMovieListDataSource.getMovieList(genreId, page)
        }

    }
}