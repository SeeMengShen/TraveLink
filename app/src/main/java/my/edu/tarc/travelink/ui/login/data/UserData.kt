package my.edu.tarc.travelink.ui.login.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

var CURRENT_USER = User()

data class User (
    @DocumentId
    var userEmail       : String = "",
    var userPassword    : String = "",
    var userName        : String = ""
)

val USERS = Firebase.firestore.collection("users")