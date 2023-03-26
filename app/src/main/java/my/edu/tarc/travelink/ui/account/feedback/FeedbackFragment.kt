package my.edu.tarc.travelink.ui.account.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentAboutUsBinding
import my.edu.tarc.travelink.databinding.FragmentFeedbackBinding

class FeedbackFragment : Fragment() {

    private lateinit var binding: FragmentFeedbackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)

        val navController = findNavController()

        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.INVISIBLE

        return binding.root
    }
}