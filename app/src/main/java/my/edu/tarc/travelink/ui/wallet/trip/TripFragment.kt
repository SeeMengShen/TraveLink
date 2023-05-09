package my.edu.tarc.travelink.ui.wallet.trip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.integrity.internal.l
import com.google.android.play.integrity.internal.t
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentTripBinding
import my.edu.tarc.travelink.databinding.FragmentWalletBinding
import my.edu.tarc.travelink.ui.account.data.CURRENT_USER
import my.edu.tarc.travelink.ui.account.data.UserViewModel
import my.edu.tarc.travelink.ui.wallet.data.TripViewModel
import my.edu.tarc.travelink.ui.util.TripAdapter
import my.edu.tarc.travelink.ui.wallet.data.Trip
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class TripFragment : Fragment() {
    private lateinit var binding: FragmentTripBinding
    private val nav by lazy { findNavController() }
    private val tripViewModel: TripViewModel by activityViewModels()

    private val timeRange = arrayOf("All", "Last 7 days", "Last 30 days")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTripBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var selectedDay = 0

        //spinner adapter
        val spinnerAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, timeRange)

        binding.filterSpinner.adapter = spinnerAdapter
        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if (position == 0) {
                    selectedDay = 0
                } else if (position == 1) {
                    selectedDay = 7
                } else {
                    selectedDay = 30
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedDay = 0
            }

        }


        //sorting button
        binding.sortButton.setOnClickListener {
                tripViewModel.earliestToLatest = !tripViewModel.earliestToLatest

            binding.sortButton.scaleY =binding.sortButton.scaleY * -1

        }

        //do the recycle view
        val adapter = TripAdapter()

        tripViewModel.tripHistory.observe(viewLifecycleOwner, Observer {
            CURRENT_USER.value!!.trips?.forEach { trip ->
                tripViewModel.addExistingTrip(trip)
            }

            if (!it.isNullOrEmpty()) {

                var displayTrip = mutableListOf<Trip>()

                //display all if no selected and all is selected
                if (selectedDay == 0) {

                    displayTrip = it.toMutableList()


                } else {
                    val dateEarliest = getEarliestDay(selectedDay)
                    //start filter the trips history
                    it.forEach { trip ->
                        //this trip is later (in the range)
                        if (trip.boardingDateTime.compareTo(dateEarliest) > 0) {
                            displayTrip.add(trip)
                        }
                    }
                }

                //sort list
                if (tripViewModel.earliestToLatest) {
                    displayTrip.sortBy { it.boardingDateTime }
                }else{
                    displayTrip.sortByDescending { it.boardingDateTime }
                }
                adapter.setTrips(displayTrip)

            }
        })
        binding.tripRecycleView.adapter = adapter


    }


    private fun getEarliestDay(selectedDay: Int): Date {
        //get today date
        val dateToday = Date()

        //get the earliest date
        val localDateTimeToday: LocalDateTime =
            LocalDateTime.ofInstant(dateToday.toInstant(), ZoneId.systemDefault())
        val localDateTimeEarliest = localDateTimeToday.minusDays(selectedDay.toLong())
        val dateEarliest =
            Date.from(localDateTimeEarliest.atZone(ZoneId.systemDefault()).toInstant())

        return dateEarliest

    }
}