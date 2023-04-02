package my.edu.tarc.travelink.ui.wallet.trip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentTripBinding
import my.edu.tarc.travelink.databinding.FragmentWalletBinding
class TripFragment : Fragment() {
    private lateinit var binding: FragmentTripBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTripBinding.inflate(inflater, container, false)
        return binding.root
    }
}