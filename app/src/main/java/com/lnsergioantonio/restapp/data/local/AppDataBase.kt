package com.lnsergioantonio.restapp.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lnsergioantonio.restapp.data.local.dao.RequestResponseDao
import com.lnsergioantonio.restapp.data.local.entitues.RequestResponseEntity

fun provideDatabase(appContext: Application) =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "RestApp")
                //.addMigrations(MIGRATION_1_TO_2, MIGRATION_2_TO_111)  //Only update the schema much recomonded
                .build()

fun provideRequestResponseDao(database: AppDatabase) = database.regionDao()

@Database(
        entities = [RequestResponseEntity::class],
        version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun regionDao(): RequestResponseDao
}