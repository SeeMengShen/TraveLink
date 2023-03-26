package my.edu.tarc.travelink.ui.account.aboutUs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)

        val navController = findNavController()

        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.INVISIBLE

        with(binding){
            aboutUsEmail.setOnClickListener{
                val openEmail = Intent(Intent.ACTION_SENDTO)
                openEmail.data = Uri.parse("mailto:cs_traveller@gmail.com")
                startActivity(openEmail)
            }

            aboutUsPhoneBtn.setOnClickListener {
                val openPhone = Intent(Intent.ACTION_VIEW)
                openPhone.data = Uri.parse("tel:0341450123")
                startActivity(openPhone)
            }
        }

        return binding.root
    }
}