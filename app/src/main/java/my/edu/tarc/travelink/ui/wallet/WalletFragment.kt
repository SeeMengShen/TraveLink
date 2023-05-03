package my.edu.tarc.travelink.ui.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentWalletBinding
import my.edu.tarc.travelink.ui.util.WalletTabAdapter
import my.edu.tarc.travelink.ui.wallet.balance.BalanceFragment
import my.edu.tarc.travelink.ui.wallet.trip.TripFragment

class WalletFragment : Fragment() {


    private lateinit var binding: FragmentWalletBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentWalletBinding.inflate(inflater, container, false)

        val fragments = listOf(
            BalanceFragment(),
            TripFragment()
        )

        val adapter = WalletTabAdapter(fragments,this)
        binding.walletViewPager.adapter = adapter

        TabLayoutMediator(binding.walletTabLayout, binding.walletViewPager){ tab, position ->
            tab.text = when(position){
                0 -> "Balance"
                1 -> "History"
                else -> ""
            }

        }.attach()



        return binding.root
    }

}