package ctrl.vanya.movietmdb.feature.movie_detail.di

import ctrl.vanya.movietmdb.data.repository.MovieDetailRepository
import ctrl.vanya.movietmdb.data.source.remote.RemoteMovieDetailDataSource
import dagger.Module
import dagger.Provides

@Module
class MovieDetailModule {
    @Provides
    fun provideMovieDetailRepository(
        remoteMovieDetailDataSource: RemoteMovieDetailDataSource
    ): MovieDetailRepository {
        return MovieDetailRepository.MovieDetailRepositoryImpl(remoteMovieDetailDataSource)
    }
}