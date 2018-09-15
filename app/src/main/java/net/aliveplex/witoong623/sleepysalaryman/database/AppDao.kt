package net.aliveplex.witoong623.sleepysalaryman.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface AppDao {
    @Insert
    fun saveLocation(location: Location)

    @Delete
    fun deleteLocation(location: Location)

    @Query("select * from locations")
    fun getAllLocations(): LiveData<List<Location>>
}