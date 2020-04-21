package ctrl.vanya.movietmdb.feature.movie_list.di

import ctrl.vanya.movietmdb.core.di.CoreModule
import ctrl.vanya.movietmdb.data.di.DataModule
import ctrl.vanya.movietmdb.feature.movie_list.ui.MovieListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MovieListModule::class,
        MovieListViewModelModule::class,
        CoreModule::class,
        DataModule::class
    ]
)
interface MovieListComponent {
    fun inject(activity: MovieListActivity)
}