package ctrl.vanya.movietmdb.core.di

import android.content.Context
import ctrl.vanya.movietmdb.core.utils.AppDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class CoreModule(private val context: Context) {

    @Provides
    fun providesContext(): Context {
        return context
    }

    @Provides
    fun providesAppDispatcher(): AppDispatchers = AppDispatchers(Dispatchers.Main, Dispatchers.IO)

}