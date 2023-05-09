package my.edu.tarc.travelink.ui.wallet.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*

class TripRepository(private val tripDao: TripDao) {

    val allTrips: LiveData<List<Trip>> = tripDao.getAlTrip()

    companion object {
        val database =
            Firebase.database("https://travelink-dc333-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(trip: Trip): Long {
        val id =  tripDao.insert(trip)
        tripDao.update(trip)
        return id
    }

    @WorkerThread
    suspend fun clear(){
        tripDao.clear()
    }

    @WorkerThread
    suspend fun delete(trip: Trip) {
        tripDao.delete(trip)
    }

    @WorkerThread
    suspend fun update(trip: Trip) {
        tripDao.update(trip)
    }

    @WorkerThread
    suspend fun addCount(stationID: Int, typeOfCount: String) {

        val today = LocalDate.now()


        //get count first
        database.child("station").child(stationID.toString()).child(today.toString()).child(typeOfCount).get()
            .addOnSuccessListener {

                if (!it.exists()) {
                    database.child("station").child(stationID.toString()).child(today.toString()).child(typeOfCount).setValue(1)
                    return@addOnSuccessListener
                }

                //add count
                val value = it.value.toString().toInt()
                database.child("station").child(stationID.toString()).child(today.toString()).child(typeOfCount).setValue(value + 1)
            }


    }

}