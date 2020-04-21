package ctrl.vanya.movietmdb.feature.movie_list.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ctrl.vanya.movietmdb.core.base.BaseViewModelFactory
import ctrl.vanya.movietmdb.core.base.ViewModelKey
import ctrl.vanya.movietmdb.feature.genre.ui.MainViewModel
import ctrl.vanya.movietmdb.feature.movie_list.ui.MovieListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieListViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    internal abstract fun bindDriverViewModel(viewModel: MovieListViewModel): ViewModel
}