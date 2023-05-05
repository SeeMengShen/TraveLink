package my.edu.tarc.travelink.ui.wallet.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TripRepository(private val tripDao: TripDao) {

    val allTrips: LiveData<List<Trip>> = tripDao.getAlTrip()


    companion object {
        val database =
            Firebase.database("https://mad-ass-94890-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

    }

    fun sync(){
        tripDao.getAlTrip()
    }

    /*init {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                TripRepository.snapshot = snapshot
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }*/

    @Suppress("RedundantSuspendModifier")
    /*@WorkerThread
    suspend fun add(trip: Trip): Long {
        return tripDao.insert(trip)
    }*/

    @WorkerThread
    suspend fun add(trip: Trip): Long {
        val id =  tripDao.insert(trip)
        tripDao.update(trip)
        return id
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

        //get count first
        database.child("station").child(stationID.toString()).child(typeOfCount).get()
            .addOnSuccessListener {

                if (!it.exists()) {
                    return@addOnSuccessListener
                }

                //add count
                val value = it.value.toString().toInt()
                database.child("station").child(stationID.toString()).child(typeOfCount).setValue(value +1)
            }


    }

}