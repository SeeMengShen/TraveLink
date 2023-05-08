package my.edu.tarc.travelink.ui.account.data

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue.arrayUnion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.ui.login.data.CURRENT_USER
import my.edu.tarc.travelink.ui.login.data.User
import my.edu.tarc.travelink.ui.wallet.data.Trip

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val firebaseUser =
        Firebase.firestore.collection("users").document(CURRENT_USER.value!!.email)


    @WorkerThread
    fun updateTrips(newTrips: List<Trip>?) = viewModelScope.launch {
        CURRENT_USER.value!!.trips = newTrips
        firebaseUser.update("trips", CURRENT_USER.value!!.trips)
    }

    @WorkerThread
    fun topup(value: Float) = viewModelScope.launch {
        CURRENT_USER.value!!.balance += value
        firebaseUser.update("balance", CURRENT_USER.value!!.balance)
    }

    @WorkerThread
    fun deduct(value: Float) = viewModelScope.launch {
        CURRENT_USER.value!!.balance -= value
        firebaseUser.update("balance", CURRENT_USER.value!!.balance)
    }

    @WorkerThread
    fun updateUser(
        newName: String,
        newPhone: String,
        newGender: String,
        newMalaysian: Boolean,
        newIdNum: String
    ) {
        with(CURRENT_USER.value!!) {
            name = newName
            phone = newPhone
            gender = newGender
            malaysian = newMalaysian
            idNum = newIdNum
        }

        firebaseUser.update(
            "name",
            newName,
            "phone",
            newPhone,
            "gender",
            newGender,
            "malaysian",
            newMalaysian,
            "idNum",
            newIdNum
        )
    }

    fun validate(name: String): String {

        return when (name) {
            "" -> "Don't leave your name blank!"
            else -> ""
        }
    }
}