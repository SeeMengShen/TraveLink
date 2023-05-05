package my.edu.tarc.travelink.ui.wallet.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "Trip")
data class Trip(

    @PrimaryKey(autoGenerate = true)
    var tripID: Int = 0,
    var boardingStationID: String = "",
    var boardingStation: String = "",
    var boardingDateTime: Date = Date(),


    var dropOffStationID: String? = null,
    var dropOffStation: String? = null,
    var dropOffDateTime: Date? = null,
    var fare: Float? = null

) {


    companion object {
        private const val dateTimePattern = "yyyy-MM-dd HH:mm:ss"
        private val dateTimeFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern(dateTimePattern)


        fun stringToDateTime(string: String?): Date?{
            try{
                val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                return dateFormat.parse(string)
            }catch (ex :Exception){
                return null
            }
        }

        fun dateTimeToString(dateTime: Date?): String?{
            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            if(dateTime != null){
                return dateFormat.format(dateTime)
            }
            return null
        }

    }

    override fun toString(): String {
        return "$tripID,$boardingStationID,$boardingStation,${boardingDateTime},$dropOffStationID,$dropOffStation,${dropOffDateTime},$fare"
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