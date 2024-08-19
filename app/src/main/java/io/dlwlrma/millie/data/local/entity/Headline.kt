package io.dlwlrma.millie.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable

@Entity
data class Headline(
    @PrimaryKey
    val id: Long? = null,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    @Nullable
    val description: String?,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "url_to_image")
    @Nullable
    val urlToImage: String?,
    @ColumnInfo(name = "published_at")
    val publishedAt: String,
    @ColumnInfo(name = "content")
    @Nullable
    val content: String?
)
