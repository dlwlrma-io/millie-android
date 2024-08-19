package io.dlwlrma.millie.domain.repository

import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.toFlow
import io.dlwlrma.millie.BuildConfig
import io.dlwlrma.millie.data.local.AppDatabase
import io.dlwlrma.millie.data.local.entity.Headline
import io.dlwlrma.millie.data.remote.NewService
import io.dlwlrma.millie.domain.model.News
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepository(
    private val service: NewService,
    private val database: AppDatabase
) {

    suspend fun getNews(): Flow<List<News>> = flow {
        val response = service.getHeadlines(apiKey = BuildConfig.NEWS_API_KEY)
            .suspendOnError {
                val news = getNewsFromDatabase()
                emit(news)
            }.toFlow()

        response.collect {
            val news = it.articles.map {
                News(
                    author = it.author,
                    title = it.title,
                    description = it.description,
                    url = it.url,
                    urlToImage = it.urlToImage,
                    publishedAt = it.publishedAt,
                    content = it.content
                )
            }

            insertNewsToDatabase(news)
            emit(news)
        }
    }.flowOn(IO)

    private fun getNewsFromDatabase(): List<News> {
        val dao = database.headlineDao()
        val entities = dao.getAll()
        return entities.map {
            News(
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }
    }

    private fun insertNewsToDatabase(news: List<News>) {
        val entities = news.map {
            Headline(
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }

        val dao = database.headlineDao()
        dao.insertAll(*entities.toTypedArray())
    }
}