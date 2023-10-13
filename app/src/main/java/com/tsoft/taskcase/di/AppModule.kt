package com.tsoft.taskcase.di

import android.content.Context
import androidx.room.Room
import com.tsoft.taskcase.remote.APIInterface
import com.tsoft.taskcase.repo.ImageRepository
import com.tsoft.taskcase.repo.ImageRepository_Impl
import com.tsoft.taskcase.repo.UserRepository
import com.tsoft.taskcase.repo.UserRepository_Impl
import com.tsoft.taskcase.storage.dao.ImageHitDao
import com.tsoft.taskcase.storage.database.ImageHitDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        imageHitDao: ImageHitDao
    ): ImageRepository = ImageRepository_Impl(retrofitAPI, imageHitDao)

    @Singleton
    @Provides
    fun UserRepositoryProvider(): UserRepository = UserRepository_Impl()

    @Singleton
    @Provides
    fun injectImageHitDao(database: ImageHitDb) = database.imageHitDao()

    @Singleton
    @Provides
    fun injectImageHitDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context, ImageHitDb::class.java, "imageHitDb"
        ).fallbackToDestructiveMigration().build()

}