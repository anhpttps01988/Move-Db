package anh.ptt.hometest.common.dagger

import android.app.Application
import anh.ptt.hometest.MovieDBApp
import anh.ptt.hometest.data.source.DataRepository
import anh.ptt.hometest.data.source.dagger.DataRepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataRepositoryModule::class,
        AndroidSupportInjectionModule::class,
        BaseActivityBindingModule::class,
        NetModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: MovieDBApp)

    override fun inject(instance: DaggerApplication?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
