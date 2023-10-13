package com.tsoft.taskcase.di

import com.tsoft.taskcase.remote.APIInterface
import com.tsoft.taskcase.repo.ImageRepository
import com.tsoft.taskcase.repo.ImageRepository_Impl
import com.tsoft.taskcase.repo.UserRepository
import com.tsoft.taskcase.repo.UserRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun ImageRepositoryProvider(
        retrofitAPI: APIInterface,
    ): ImageRepository = ImageRepository_Impl(retrofitAPI)

    @Singleton
    @Provides
    fun UserRepositoryProvider(): UserRepository = UserRepository_Impl()

}