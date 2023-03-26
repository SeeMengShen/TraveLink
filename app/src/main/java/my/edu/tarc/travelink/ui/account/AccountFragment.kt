package my.edu.tarc.travelink.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.ActivityMainBinding
import my.edu.tarc.travelink.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAccountBinding.inflate(inflater, container, false)

        val navController = findNavController()

        with(binding) {
            accountEditDetailsBtn.setOnClickListener() { navController.navigate(R.id.editDetailsFragment) }
            accountFeedbackBtn.setOnClickListener() { navController.navigate(R.id.feedbackFragment) }
            accountAboutUsBtn.setOnClickListener() { navController.navigate(R.id.aboutUsFragment) }
        }

        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE

        return binding.root
    }
}