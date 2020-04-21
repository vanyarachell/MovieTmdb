package ctrl.vanya.movietmdb.data.repository

import ctrl.vanya.movietmdb.core.model.MovieDetailMdl
import ctrl.vanya.movietmdb.data.source.MovieDetailDataSource
import ctrl.vanya.movietmdb.data.utils.ResultState
import javax.inject.Inject

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: String): ResultState<MovieDetailMdl>

    class MovieDetailRepositoryImpl @Inject constructor(private val remoteMovieDetailDataSource: MovieDetailDataSource) : MovieDetailRepository {
        override suspend fun getMovieDetail(movieId: String): ResultState<MovieDetailMdl> {
            return remoteMovieDetailDataSource.getMovieDetail(movieId)
        }

    }
}