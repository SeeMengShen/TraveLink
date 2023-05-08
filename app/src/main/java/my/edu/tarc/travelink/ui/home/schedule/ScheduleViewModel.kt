package my.edu.tarc.travelink.ui.home.schedule

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import my.edu.tarc.travelink.ui.home.data.DBNews
import my.edu.tarc.travelink.ui.home.data.DBSchedule
import my.edu.tarc.travelink.ui.home.data.News
import my.edu.tarc.travelink.ui.home.data.Schedule
import my.edu.tarc.travelink.ui.home.news.NewsViewModel

class ScheduleViewModel : ViewModel() {
    private val _schedule = MutableLiveData<MutableList<Schedule>>()

    private val _filteredItems = MutableLiveData<List<Schedule>>()
    val filteredItems: LiveData<List<Schedule>>? get() = _filteredItems

    companion object {
        val database =
            Firebase.database("https://travelink-dc333-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    }

    init {
        downloadSchedule()
    }

    fun get(scheduleName: String): Schedule? {
        return _schedule.value!!.find { a -> a.scheduleTitle == scheduleName }
    }


    fun filterItems(query: String) {
        val items = _schedule.value ?: emptyList()
        val filtered = items.filter { it.scheduleTitle.contains(query, ignoreCase = true) }
        _filteredItems.value = filtered
    }

    @WorkerThread
    fun downloadSchedule() = viewModelScope.launch {
        database.child("schedule").get()
            .addOnSuccessListener {

                if (!it.exists()) {
                    return@addOnSuccessListener
                }

                // Convert snapshot to list
                val dbScheduleList = it.getValue<List<DBSchedule>>()
                _schedule.value = dbScheduletoSchedule(dbScheduleList!!)

                _filteredItems.value = _schedule.value
            }
    }

    private fun dbScheduletoSchedule(dbScheduleList: List<DBSchedule>): MutableList<Schedule> {
        val newScheduleList: MutableList<Schedule> = mutableListOf()
        dbScheduleList.forEachIndexed { index, dbSchedule ->
            newScheduleList.add(index, Schedule(index, dbSchedule.scheduleTitle))
        }
        return newScheduleList
    }
}