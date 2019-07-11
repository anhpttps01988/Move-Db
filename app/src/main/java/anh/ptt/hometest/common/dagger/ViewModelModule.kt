package anh.ptt.hometest.common.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import anh.ptt.hometest.activities.main.viewmodel.MainActivityViewModel
import anh.ptt.hometest.common.dagger.scopes.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun mainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    abstract fun viewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}