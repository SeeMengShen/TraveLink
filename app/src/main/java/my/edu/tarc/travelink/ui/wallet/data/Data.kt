package my.edu.tarc.travelink.ui.wallet.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "Trip")
data class Trip(

    @PrimaryKey(autoGenerate = true)
    var tripID: Int = 0,
    var boardingStationID: String = "",
    var boardingStation: String = "",
    var boardingDateTime: LocalDateTime,


    var dropOffStationID: String? = null,
    var dropOffStation: String? = null,
    var dropOffDateTime: LocalDateTime? = null,
    var fare: Float? = null

) {


    companion object {
        private const val dateTimePattern = "yyyy-MM-dd HH:mm:ss"
        private val dateTimeFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern(dateTimePattern)


        fun stringToDateTime(string: String?): LocalDateTime? {
            try{
                return LocalDateTime.parse(string, dateTimeFormatter)
            }catch (ex :Exception){
                return null
            }
        }

        fun dateTimeToString(dateTime: LocalDateTime?): String? {
            return dateTime?.format(dateTimeFormatter)
        }

    }

    override fun toString(): String {
        return "$tripID,$boardingStationID,$boardingStation,${boardingDateTimeToString()},$dropOffStationID,$dropOffStation,${dropOffDateTimeToString()},$fare"
    }


    fun isBoarding(): Boolean {
        return boardingStationID.isNotEmpty() && dropOffStationID.isNullOrEmpty()
    }

    fun boardingDateTimeToString(): String{
        return dateTimeToString(boardingDateTime)!!
    }

    fun dropOffDateTimeToString(): String? {
        return dropOffDateTime?.let { dateTimeToString(it) }
    }


}