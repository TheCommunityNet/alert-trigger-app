package wiki.comnet.alerttrigger.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShellyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shellyDao(): ShellyDao
}
