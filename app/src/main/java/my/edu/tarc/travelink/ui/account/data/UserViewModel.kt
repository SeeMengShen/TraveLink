package my.edu.tarc.travelink.ui.account.data

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
import androidx.lifecycle.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue.arrayUnion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.ui.wallet.data.Trip
import my.edu.tarc.travelink.ui.wallet.data.TripRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        val database =
            Firebase.database("https://travelink-dc333-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    }

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

    @WorkerThread
    fun updateSeen(newsID: Int){
        CURRENT_USER.value!!.seenPost.add(newsID)
        firebaseUser.collection("seenNews").document(newsID.toString()).set({})
        addNewsCountIntoRTDB(newsID)
    }

    @WorkerThread
    fun readSeen(){
        firebaseUser.collection("seenNews").get().addOnSuccessListener{documents->

            //get the seen news id list
            val seenList = mutableListOf<Int>()
            documents.forEach {doc->
                seenList.add(doc.id.toInt())
            }

            //set it into current user
            CURRENT_USER.value!!.seenPost = seenList

        }
    }

    @WorkerThread
    fun addNewsCountIntoRTDB(newsID: Int){
        //get clicked count
        database.child("news").child(newsID.toString()).child("userViewed").get().addOnSuccessListener {

            if (!it.exists()) {
                database.child("news").child(newsID.toString()).child("userViewed").setValue(1)
                return@addOnSuccessListener
            }

            val value = it.value.toString().toInt()
            database.child("news").child(newsID.toString()).child("userViewed").setValue(value+ 1)
        }
    }
}