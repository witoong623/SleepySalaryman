package net.aliveplex.witoong623.sleepysalaryman.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import android.os.AsyncTask
import net.aliveplex.witoong623.sleepysalaryman.database.CurrentAppDb
import net.aliveplex.witoong623.sleepysalaryman.database.Location

class AddLocationViewModel : ViewModel() {
    private val mAddLocationResult = MutableLiveData<AddLocationResult>()

    val addLocationResult: LiveData<AddLocationResult>
        get() = mAddLocationResult

    fun saveLocation(location: Location) {
        AsyncTask.execute {
            val dao = CurrentAppDb.Dao()
            dao.saveLocation(location)

            mAddLocationResult.postValue(AddLocationResult(AddLocationStatus.Success, null))
        }
    }
}

data class AddLocationResult(val addLocationStatus: AddLocationStatus, val msg: String?)

enum class AddLocationStatus {
    Success, Error
}
