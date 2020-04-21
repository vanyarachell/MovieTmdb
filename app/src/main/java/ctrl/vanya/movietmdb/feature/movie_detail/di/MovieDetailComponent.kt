package ctrl.vanya.movietmdb.feature.movie_detail.di

import ctrl.vanya.movietmdb.core.di.CoreModule
import ctrl.vanya.movietmdb.data.di.DataModule
import ctrl.vanya.movietmdb.feature.movie_detail.ui.MovieDetailActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MovieDetailModule::class,
        MovieDetailViewModelModule::class,
        CoreModule::class,
        DataModule::class
    ]
)
interface MovieDetailComponent {
    fun inject(activity: MovieDetailActivity)
}