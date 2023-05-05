package my.edu.tarc.travelink.ui.wallet.trip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.marginBottom
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentTripBinding
import my.edu.tarc.travelink.databinding.FragmentWalletBinding
import my.edu.tarc.travelink.ui.login.data.CURRENT_USER
import my.edu.tarc.travelink.ui.wallet.data.TripViewModel
import my.edu.tarc.travelink.ui.util.TripAdapter

class TripFragment : Fragment() {
    private lateinit var binding: FragmentTripBinding
    private val nav by lazy { findNavController() }
    private val tripViewModel: TripViewModel by activityViewModels()

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

        //do the recycle view
        val adapter = TripAdapter()

        tripViewModel.tripHistory.observe(viewLifecycleOwner, Observer {
            CURRENT_USER.value!!.trips?.forEach { trip ->
                tripViewModel.addExistingTrip(trip)
            }

            if (!it.isNullOrEmpty()) {
                adapter.setTrips(it)
            }
        })
        binding.tripRecycleView.adapter = adapter
    }
}