package my.edu.tarc.travelink.ui.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentScanSuccessfulBinding
import my.edu.tarc.travelink.ui.account.data.UserViewModel
import my.edu.tarc.travelink.ui.login.data.CURRENT_USER
import my.edu.tarc.travelink.ui.wallet.data.TripViewModel

class ScanSuccessfulFragment : Fragment() {

    private val tripViewModel: TripViewModel by activityViewModels()
    private val uvm: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentScanSuccessfulBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScanSuccessfulBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val station: String
        val dateTimeString: String

        with(binding) {
            //if is boarding
            if (tripViewModel.currentTrip!!.isBoarding()) {
                scanSuccessfulTitleTextView.text = getString(R.string.boardingSuccessfully)
                scanSuccessfulFareLabelTextView.visibility = View.INVISIBLE
                scanSuccessfulFareTextView.visibility = View.INVISIBLE

                scanSuccessfulStationNameTextView.text = tripViewModel.currentTrip!!.boardingStation
                scanSuccessfulTimeTextView.text =
                    tripViewModel.currentTrip!!.boardingDateTimeToString()

                //add into db
                tripViewModel.addTrip(tripViewModel.currentTrip!!)
                tripViewModel.addCount(
                    tripViewModel.currentTrip!!.boardingStationID.toInt(),
                    TripViewModel.boardingCount
                )

            }

            //if drop off
            else {
                scanSuccessfulTitleTextView.text = getString(R.string.dropOffSuccessfully)
                scanSuccessfulFareLabelTextView.visibility = View.VISIBLE
                scanSuccessfulFareTextView.visibility = View.VISIBLE

                scanSuccessfulStationNameTextView.text = tripViewModel.currentTrip!!.dropOffStation
                scanSuccessfulTimeTextView.text =
                    tripViewModel.currentTrip!!.dropOffDateTimeToString()
                scanSuccessfulFareTextView.text = tripViewModel.currentTrip!!.fare.toString()

                //update into db
                tripViewModel.updateTrip(tripViewModel.currentTrip!!)
                tripViewModel.addCount(
                    tripViewModel.currentTrip!!.dropOffStationID!!.toInt(),
                    TripViewModel.dropOffCount
                )

                uvm.deduct(tripViewModel.currentTrip!!.fare!!)

                tripViewModel.currentTrip = null
            }

            tripViewModel.tripHistory.observe(requireActivity(), Observer {
                uvm.updateTrips(tripViewModel.tripHistory.value)
            })
        }
        binding.scanSuccessfulOKButton.setOnClickListener {
            findNavController().navigateUp()
            findNavController().navigateUp()
        }
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}