package io.dlwlrma.millie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.dlwlrma.millie.data.local.dao.HeadlineDao
import io.dlwlrma.millie.data.local.entity.Headline

@Database(entities = [Headline::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun headlineDao(): HeadlineDao
}