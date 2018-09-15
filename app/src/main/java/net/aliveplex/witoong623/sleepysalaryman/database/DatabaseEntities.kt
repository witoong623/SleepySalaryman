package net.aliveplex.witoong623.sleepysalaryman.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(@ColumnInfo(name = "name") val name: String,
                    @ColumnInfo(name = "latitude") val latitude: Double,
                    @ColumnInfo(name = "longitude") val longitude: Double,
                    @ColumnInfo(name = "radius") val radius: Int,
                    @PrimaryKey(autoGenerate = true) val id: Int? = null)
