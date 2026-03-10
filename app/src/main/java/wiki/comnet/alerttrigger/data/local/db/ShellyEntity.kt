package wiki.comnet.alerttrigger.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shellies")
data class ShellyEntity(
    @PrimaryKey val id: String,
    val name: String,
)