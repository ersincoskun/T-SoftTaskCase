package com.tsoft.taskcase.di

import com.tsoft.taskcase.repo.UserRepository
import com.tsoft.taskcase.repo.UserRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun UserRepositoryProvider(): UserRepository = UserRepository_Impl()

}