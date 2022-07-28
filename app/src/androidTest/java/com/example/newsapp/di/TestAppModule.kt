package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.db.NewsDB
import com.example.newsapp.db.NewsDao
import com.example.newsapp.network.ApiInterface
import com.example.newsapp.ui.articles.ArticlesRepository
import com.example.newsapp.ui.articles.ArticlesRepositoryImpl
import com.example.newsapp.ui.home.headlines.HeadlinesRepository
import com.example.newsapp.ui.home.headlines.HeadlinesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context): RoomDatabase {
        return Room.inMemoryDatabaseBuilder(context, NewsDB::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Named("test_dao")
    fun provideTestNewsDao(@Named("test_db") newsDB: NewsDB): NewsDao {
        return newsDB.getNewsDAO()
    }

    @Provides
    @Named("test_client")
    fun provideTestHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Named("test_retrofit")
    fun provideTestRetrofit(@Named("test_client") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://5d494e242d59e50014f21240.mockapi.io/api/")
            .build()
    }

    @Provides
    @Named("test_interface")
    fun provideTestApiInterface(@Named("test_retrofit") retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Named("test_articleRepo")
    fun provideTestArticlesRepository(
        api: ApiInterface,
        newsDao: NewsDao
    ): ArticlesRepository {
        return ArticlesRepositoryImpl(api, newsDao)
    }
}