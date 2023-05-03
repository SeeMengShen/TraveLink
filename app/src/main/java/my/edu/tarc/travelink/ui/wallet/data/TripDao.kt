package my.edu.tarc.travelink.ui.wallet.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao {
    @Query("SELECT * FROM Trip")
    fun getAlTrip(): LiveData<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trip: Trip): Long

    @Delete
    suspend fun delete(trip: Trip)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(trip: Trip)
}