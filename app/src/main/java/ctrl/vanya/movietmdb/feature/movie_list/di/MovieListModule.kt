package ctrl.vanya.movietmdb.feature.movie_list.di

import ctrl.vanya.movietmdb.data.repository.MovieListRepository
import ctrl.vanya.movietmdb.data.source.remote.RemoteMovieListDataSource
import dagger.Module
import dagger.Provides

@Module
class MovieListModule {
    @Provides
    fun provideMovieListRepository(
        remoteMovieListDataSource: RemoteMovieListDataSource
    ): MovieListRepository {
        return MovieListRepository.MovieListRepositoryImpl(remoteMovieListDataSource)
    }
}