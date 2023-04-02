package my.edu.tarc.travelink.ui.data

import java.util.*

data class Trip(

    var tripID: String = "0",
    var boardingStation: String = "",
    var dropOffStation: String = "",
    var boardingDateTime: Date = Date(),
    var dropOffDateTime: Date = Date(),
    var fare: Float = 0.0f

)