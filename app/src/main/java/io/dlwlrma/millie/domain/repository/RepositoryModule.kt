package io.dlwlrma.millie.domain.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dlwlrma.millie.data.local.AppDatabase
import io.dlwlrma.millie.data.remote.NewService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(service: NewService, database: AppDatabase): NewsRepository {
        return NewsRepository(service, database)
    }
}
