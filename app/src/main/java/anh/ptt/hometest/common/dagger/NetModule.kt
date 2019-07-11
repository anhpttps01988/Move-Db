package anh.ptt.hometest.common.dagger

import android.app.Application
import anh.ptt.hometest.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {

    @Singleton
    @Provides
    fun okHttpClient(application: Application): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newBuilder: Request.Builder = request.newBuilder()
                    .addHeader("User-Agent", "Android")

                chain.proceed(newBuilder.build())
            }
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(true)
            .connectTimeout(Constants.Net.NET_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(Constants.Net.NET_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(Constants.Net.BASE_URL)
        return builder.addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

}