package wiki.comnet.alerttrigger.di

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module
import wiki.comnet.alerttrigger.data.local.db.AppDatabase
import wiki.comnet.alerttrigger.data.local.db.ShellyDao

fun createDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build()

fun createUserDao(database: AppDatabase): ShellyDao = database.shellyDao()

val databaseModule = module {
    single { createDatabase(get()) }
    single { createUserDao(get()) }
}