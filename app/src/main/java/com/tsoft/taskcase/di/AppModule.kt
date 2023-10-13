package com.tsoft.taskcase.di

import android.content.Context
import com.tsoft.taskcase.remote.APIInterface
import com.tsoft.taskcase.repo.UserRepository
import com.tsoft.taskcase.repo.UserRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofitAPI(): APIInterface {
        val okhttp = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .baseUrl("https://pixabay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()
            .create(APIInterface::class.java)
    }

    @Singleton
    @Provides
    fun UserRepositoryProvider(): UserRepository = UserRepository_Impl()

}