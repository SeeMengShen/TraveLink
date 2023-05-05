package my.edu.tarc.travelink.ui.wallet.data

import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.*

class TripConverter {

    @TypeConverter
    fun dateToString(dateTime: Date?): String?{
        return Trip.dateTimeToString(dateTime)

    }

    @TypeConverter
    fun stringToDateTime(string: String?):Date?{
        return Trip.stringToDateTime(string)
    }
}