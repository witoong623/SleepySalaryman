package net.aliveplex.witoong623.sleepysalaryman.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(@ColumnInfo(name = "name") val name: String,
                    @ColumnInfo(name = "latitude") val latitude: Double,
                    @ColumnInfo(name = "longitude") val longitude: Double,
                    @ColumnInfo(name = "radius") val radius: Int,
                    @PrimaryKey(autoGenerate = true) val id: Int? = null)

data class Alarm(val location_id: Int,
                 val name: String,
                 val label: String,
                 val isEnable: Boolean,
                 val onMon: Boolean,
                 val onTue: Boolean,
                 val onWed: Boolean,
                 val onThu: Boolean,
                 val onFri: Boolean,
                 val onSat: Boolean,
                 val onSun: Boolean,
                 val id: Int? = null)
