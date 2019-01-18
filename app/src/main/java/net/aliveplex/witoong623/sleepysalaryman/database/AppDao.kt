package net.aliveplex.witoong623.sleepysalaryman.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AppDao {
    @Insert
    fun saveLocation(location: Location)

    @Delete
    fun deleteLocation(location: Location)

    @Query("select * from locations")
    fun getAllLocations(): LiveData<List<Location>>
}