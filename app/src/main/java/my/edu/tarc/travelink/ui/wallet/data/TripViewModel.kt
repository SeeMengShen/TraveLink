package my.edu.tarc.travelink.ui.wallet.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

import java.time.*
import javax.inject.Inject
import kotlin.text.Typography.dagger

class TripViewModel(application: Application) : AndroidViewModel(application) {


    companion object {
        final val boardingCount: String = "boardingCount"
        final val dropOffCount: String = "dropOffCount"
    }

    private val repository: TripRepository
    val tripHistory: LiveData<List<Trip>>
    var currentTrip: Trip? = null

    init {
        val tripDao = TripDatabase.getDatabase(application).tripDao()
        repository = TripRepository(tripDao)
        tripHistory = repository.allTrips
    }

    fun addTrip(trip: Trip) {
        var id: Long
        viewModelScope.launch {
            id = repository.add(trip)
            currentTrip!!.tripID = id.toInt()
        }
    }

    fun addExistingTrip(trip: Trip){
        viewModelScope.launch {
            repository.add(trip)
        }
    }

    fun updateTrip(trip: Trip) {
        viewModelScope.launch {
            repository.update(trip)
        }
    }

    fun deleteTrip(trip: Trip) {
        viewModelScope.launch {
            repository.delete(trip)
        }
    }

    fun addCount(stationID: Int, typeOfCount: String) {
        viewModelScope.launch {
            repository.addCount(stationID, typeOfCount)
        }
    }


}