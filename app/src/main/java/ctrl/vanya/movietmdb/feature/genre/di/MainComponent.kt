package ctrl.vanya.movietmdb.feature.genre.di

import ctrl.vanya.movietmdb.core.di.CoreModule
import ctrl.vanya.movietmdb.data.di.DataModule
import ctrl.vanya.movietmdb.feature.genre.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainModule::class,
        MainViewModelModule::class,
        CoreModule::class,
        DataModule::class
    ]
)
interface MainComponent {
    fun inject(activity: MainActivity)
}