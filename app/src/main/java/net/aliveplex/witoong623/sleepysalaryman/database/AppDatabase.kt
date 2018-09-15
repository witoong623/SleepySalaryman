package net.aliveplex.witoong623.sleepysalaryman.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import splitties.init.appCtx

@Database(entities = [Location::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}

object CurrentAppDb {
    private val appDb: AppDatabase = Room.databaseBuilder(appCtx, AppDatabase::class.java, "sleepysalaryman.db").build()

    fun Dao(): AppDao = appDb.appDao()
}