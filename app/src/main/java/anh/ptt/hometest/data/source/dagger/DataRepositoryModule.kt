package anh.ptt.hometest.data.source.dagger

import anh.ptt.hometest.common.dagger.scopes.Remote
import anh.ptt.hometest.data.source.DataSource
import anh.ptt.hometest.data.source.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataRepositoryModule {

    @Remote
    @Binds
    @Singleton
    abstract fun remoteDataSource(dataSource: RemoteDataSource): DataSource

}