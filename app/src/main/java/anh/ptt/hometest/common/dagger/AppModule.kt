package anh.ptt.hometest.common.dagger

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindContext(application: Application) : Context

}