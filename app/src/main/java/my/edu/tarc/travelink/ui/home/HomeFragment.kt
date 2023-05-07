package my.edu.tarc.travelink.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.android.material.tabs.TabLayoutMediator
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentHomeBinding
import my.edu.tarc.travelink.ui.home.liveBusTracking.LiveBusTrackingFragment
import my.edu.tarc.travelink.ui.home.news.NewsFragment
import my.edu.tarc.travelink.ui.home.schedule.ScheduleFragment

class HomeFragment : Fragment() {


  private var  _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val fragments = listOf(
            NewsFragment(),
            LiveBusTrackingFragment(),
            ScheduleFragment()
        )

        val adapter = HomeTabAdapter(fragments,this)
        binding.homeViewPager.adapter = adapter

        TabLayoutMediator(binding.homeTabLayout,binding.homeViewPager){tab, position ->
            tab.text = when(position){
                0 -> "News"
                1 -> "Live Bus Tracking"
                2 -> "Schedule"
                else -> ""
            }
        }.attach()

//        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}