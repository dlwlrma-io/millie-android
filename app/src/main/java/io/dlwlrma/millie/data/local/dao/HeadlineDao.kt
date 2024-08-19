package io.dlwlrma.millie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.dlwlrma.millie.data.local.entity.Headline

@Dao
interface HeadlineDao {

    @Query("SELECT * FROM headline")
    fun getAll(): List<Headline>

    @Insert
    fun insertAll(vararg headline: Headline)
}