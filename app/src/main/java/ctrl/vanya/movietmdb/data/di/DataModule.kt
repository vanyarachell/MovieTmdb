package ctrl.vanya.movietmdb.data.di

import ctrl.vanya.movietmdb.BuildConfig
import ctrl.vanya.movietmdb.data.source.MainDataSource
import ctrl.vanya.movietmdb.data.source.MovieDetailDataSource
import ctrl.vanya.movietmdb.data.source.MovieListDataSource
import ctrl.vanya.movietmdb.data.source.remote.*
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    // Service
    @Provides
    fun provideMainService(): MainService {
        return ApiClient.retrofitClient(
            "https://api.themoviedb.org/3/").create(MainService::class.java)
    }

    @Provides
    fun provideMovieListService(): MovieListService {
        return ApiClient.retrofitClient(
            "https://api.themoviedb.org/3/").create(MovieListService::class.java)
    }

    @Provides
    fun provideMovieDetailService(): MovieDetailService {
        return ApiClient.retrofitClient(
            "https://api.themoviedb.org/3/").create(MovieDetailService::class.java)
    }

    // Remote Data Source
    @Provides
    fun provideMainDataSource(mainService: MainService): MainDataSource {
        return RemoteMainDataSource(
            mainService
        )
    }

    @Provides
    fun provideMovieListDataSource(movieListService: MovieListService): MovieListDataSource {
        return RemoteMovieListDataSource(
            movieListService
        )
    }

    @Provides
    fun provideMovieDetailDataSource(movieDetailService: MovieDetailService): MovieDetailDataSource {
        return RemoteMovieDetailDataSource(
            movieDetailService
        )
    }
}