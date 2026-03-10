package wiki.comnet.alerttrigger.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShellyDao {

    @Query("SELECT * FROM shellies")
    suspend fun getAll(): List<ShellyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shellies: List<ShellyEntity>)

    @Query("DELETE FROM shellies")
    suspend fun deleteAll()
}