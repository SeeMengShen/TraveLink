package my.edu.tarc.travelink.ui.home.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.ui.home.data.SearchResult

class ScheduleViewModel: ViewModel() {
    private val _searchResult = MutableLiveData<List<SearchResult>>()
    val searchResult: LiveData<List<SearchResult>> = _searchResult

    private val _filteredItems = MutableLiveData<List<SearchResult>>()
    val filteredItems: LiveData<List<SearchResult>>get() = _filteredItems

    val items = listOf(
        SearchResult("T250 Bus Schedule", R.drawable.t250_route,R.drawable.t250_timetable),
        SearchResult("250 Bus Schedule", R.drawable.route_250,R.drawable.timetable_250),
        SearchResult("222 Bus Schedule", R.drawable.route_222,R.drawable.timetable_222),

        )

        init {
            _searchResult.value = items
            _filteredItems.value = items
        }





        fun get(ScheduleName: String): SearchResult? {
            return items.find{a -> a.name == ScheduleName}
        }



    fun filterItems(query: String) {
        val items = _searchResult.value?: emptyList()
        val filtered = items.filter { it.name.contains(query, ignoreCase = true) }
        _filteredItems.value = filtered
    }




}