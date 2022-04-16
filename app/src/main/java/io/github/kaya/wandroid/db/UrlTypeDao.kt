package io.github.kaya.wandroid.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.kaya.wandroid.vo.UrlTypeVO

@Dao
interface UrlTypeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: UrlTypeVO)

    @Query("select type from UrlTypeVO where urlMd5=:md5")
    fun getType(md5: String): String?
}