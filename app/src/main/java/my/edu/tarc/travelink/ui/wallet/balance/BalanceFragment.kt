package my.edu.tarc.travelink.ui.wallet.balance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentBalanceBinding

class BalanceFragment : Fragment() {
    private lateinit var binding: FragmentBalanceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentBalanceBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.balanceButtonTopUp.setOnClickListener {
            findNavController().navigate(R.id.topUpFragment)
        }
    }

}