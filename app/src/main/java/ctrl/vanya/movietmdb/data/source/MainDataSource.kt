package ctrl.vanya.movietmdb.data.source

import ctrl.vanya.movietmdb.core.model.GenreListMdl
import ctrl.vanya.movietmdb.data.utils.ResultState

interface MainDataSource {
    suspend fun getGenreList(): ResultState<GenreListMdl>
}