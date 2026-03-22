package wiki.comnet.alerttrigger.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShellyCategoryDao {

    @Query("SELECT * FROM shelly_categories ORDER BY label ASC")
    suspend fun getAll(): List<ShellyCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<ShellyCategoryEntity>)

    @Query("DELETE FROM shelly_categories")
    suspend fun deleteAll()
}
