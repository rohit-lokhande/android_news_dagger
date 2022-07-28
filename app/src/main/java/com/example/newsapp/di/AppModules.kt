package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
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
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val requestBuilder: Request.Builder = it.request().newBuilder()
                requestBuilder.header("X-Api-Key", "5ee917217ac744e28728e7ff6918e09a")
                it.proceed(requestBuilder.build())
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://newsapi.org/v2/")
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDB(@ApplicationContext context: Context): NewsDB {
        return Room.databaseBuilder(context, NewsDB::class.java, "NewsDB").build()
    }

    @Provides
    fun provideNewsDao(newsDB: NewsDB): NewsDao {
        return newsDB.getNewsDAO()
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideHeadlinesRepository(api: ApiInterface, newsDao: NewsDao): HeadlinesRepository {
        return HeadlinesRepositoryImpl(api, newsDao)
    }

    @Singleton
    @Provides
    fun provideArticlesRepository(api: ApiInterface, newsDao: NewsDao): ArticlesRepository {
        return ArticlesRepositoryImpl(api, newsDao)
    }
}