package wiki.comnet.alerttrigger.di

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module
import wiki.comnet.alerttrigger.data.local.db.AppDatabase
import wiki.comnet.alerttrigger.data.local.db.ShellyCategoryDao
import wiki.comnet.alerttrigger.data.local.db.ShellyDao

fun createDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "app-database")
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()

fun createShellyDao(database: AppDatabase): ShellyDao = database.shellyDao()

fun createShellyCategoryDao(database: AppDatabase): ShellyCategoryDao =
    database.shellyCategoryDao()

val databaseModule = module {
    single { createDatabase(get()) }
    single { createShellyDao(get()) }
    single { createShellyCategoryDao(get()) }
}