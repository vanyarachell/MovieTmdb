package ctrl.vanya.movietmdb.feature.movie_detail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ctrl.vanya.movietmdb.core.base.BaseViewModelFactory
import ctrl.vanya.movietmdb.core.base.ViewModelKey
import ctrl.vanya.movietmdb.feature.movie_detail.ui.MovieDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieDetailViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    internal abstract fun bindDriverViewModel(viewModel: MovieDetailViewModel): ViewModel
}