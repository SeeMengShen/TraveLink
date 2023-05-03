package my.edu.tarc.travelink.ui.wallet.data

import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import org.json.JSONObject
import java.time.LocalDateTime

class TripConverter {

    @TypeConverter
    fun dateToString(dateTime: LocalDateTime?): String? {
        return Trip.dateTimeToString(dateTime)

    }

    @TypeConverter
    fun stringToDateTime(string: String?):LocalDateTime?{
        return Trip.stringToDateTime(string)
    }
}