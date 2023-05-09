package my.edu.tarc.travelink.ui.wallet.balance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentBalanceBinding
import my.edu.tarc.travelink.ui.account.data.CURRENT_USER
import my.edu.tarc.travelink.ui.account.data.UserViewModel

class BalanceFragment : Fragment() {
    private lateinit var binding: FragmentBalanceBinding
    private val userViewModel : UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentBalanceBinding.inflate(inflater, container, false)
        updateBalanceUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.balanceButtonTopUp.setOnClickListener {
            findNavController().navigate(R.id.topUpFragment)
        }

        if (CURRENT_USER.value!!.balance.toString().toFloat() >= 5f) {
            binding.balanceLowTextView.visibility = View.INVISIBLE
        }

        CURRENT_USER.observe(viewLifecycleOwner, Observer {
            updateBalanceUI()
        })
    }

    private fun updateBalanceUI(){
        binding.balanceTotalAmountLabel2.text = String.format("RM %.2f", CURRENT_USER.value!!.balance)
    }
}