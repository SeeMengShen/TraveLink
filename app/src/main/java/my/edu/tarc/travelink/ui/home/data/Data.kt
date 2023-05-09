package my.edu.tarc.travelink.ui.home.data

import android.graphics.Bitmap

// News class for retrieving database value (without Photo field)
data class DBNews(
    var newsTitle: String = "",
    var newsDate: String = "",
    var newsDesc: String = "",
)

data class News(
    var newsID: Int = 0,
    var newsTitle: String = "",
    var newsDate: String = "",
    var newsDesc: String = "",
    var newsPhoto: Bitmap? = null
)

// Schedule class for retrieving database value (without Photo field)
data class DBSchedule(
    var scheduleID: Int = 0,
    var scheduleTitle: String = ""
)

data class Schedule(
    var scheduleID: Int = 0,
    var scheduleTitle: String = "",
    var timetable: Bitmap? = null,
    var route : Bitmap? = null
)
