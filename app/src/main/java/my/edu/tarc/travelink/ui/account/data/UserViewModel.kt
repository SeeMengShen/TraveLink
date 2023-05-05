package my.edu.tarc.travelink.ui.account.data

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue.arrayUnion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import my.edu.tarc.travelink.ui.login.data.CURRENT_USER
import my.edu.tarc.travelink.ui.login.data.User

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val firebaseUser = Firebase.firestore.collection("users").document(CURRENT_USER.value!!.email)

    @WorkerThread
    fun updateTrips() = viewModelScope.launch{
        firebaseUser.update("trips", CURRENT_USER.value!!.trips)
    }

    fun validate(u: User): String{

        return when (u.name) {
            "" -> "Don't leave your name blank!"
            else -> ""
        }
    }
}