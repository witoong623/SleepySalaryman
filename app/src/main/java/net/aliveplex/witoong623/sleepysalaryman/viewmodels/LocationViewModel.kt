package net.aliveplex.witoong623.sleepysalaryman.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import net.aliveplex.witoong623.sleepysalaryman.database.CurrentAppDb
import net.aliveplex.witoong623.sleepysalaryman.database.Location

class LocationViewModel : ViewModel() {
    var locations: LiveData<List<Location>>? = null
        get() {
            if (field == null) {
                val dao = CurrentAppDb.Dao()
                field = dao.getAllLocations()
            }
            return field
        }
}
