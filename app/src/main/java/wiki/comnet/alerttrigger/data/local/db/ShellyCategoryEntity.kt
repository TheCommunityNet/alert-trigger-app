package wiki.comnet.alerttrigger.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shelly_categories")
data class ShellyCategoryEntity(
    @PrimaryKey val value: String,
    val label: String,
)
