package my.edu.tarc.travelink.ui.login.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.edu.tarc.travelink.ui.wallet.data.Trip
import java.util.*

//var CURRENT_USER = User()
val CURRENT_USER = MutableLiveData<User>()

data class User (
    @DocumentId
    var email: String = "",
    var name: String = "",
    var phone: String = "",
    var gender: String = "",
    var malaysian: Boolean = false,
    var idNum: String = "",
    var balance : Float = 0f,
    var trips : List<Trip>? = emptyList()
)