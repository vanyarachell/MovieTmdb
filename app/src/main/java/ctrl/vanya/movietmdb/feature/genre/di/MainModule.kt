package ctrl.vanya.movietmdb.feature.genre.di

import ctrl.vanya.movietmdb.data.repository.MainRepository
import ctrl.vanya.movietmdb.data.source.remote.RemoteMainDataSource
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun provideMainRepository(
        remoteMainDataSource: RemoteMainDataSource
    ): MainRepository {
        return MainRepository.MainRepositoryImpl(remoteMainDataSource)
    }
}