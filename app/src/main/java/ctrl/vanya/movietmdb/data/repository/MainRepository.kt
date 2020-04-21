package ctrl.vanya.movietmdb.data.repository

import ctrl.vanya.movietmdb.core.model.GenreListMdl
import ctrl.vanya.movietmdb.data.source.MainDataSource
import ctrl.vanya.movietmdb.data.utils.ResultState
import javax.inject.Inject

interface MainRepository {
    suspend fun getGenreList(): ResultState<GenreListMdl>

    class MainRepositoryImpl @Inject constructor(private val remoteMainDataSource: MainDataSource) : MainRepository {
        override suspend fun getGenreList(): ResultState<GenreListMdl> {
            return remoteMainDataSource.getGenreList()
        }

    }
}