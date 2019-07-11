package anh.ptt.hometest.common.dagger

import anh.ptt.hometest.activities.main.view.MainActivity
import anh.ptt.hometest.common.dagger.scopes.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BaseActivityBindingModule {


    @PerActivity
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity


}