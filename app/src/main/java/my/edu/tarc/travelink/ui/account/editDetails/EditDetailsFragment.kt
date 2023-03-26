package my.edu.tarc.travelink.ui.account.editDetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentAboutUsBinding
import my.edu.tarc.travelink.databinding.FragmentEditDetailsBinding

class EditDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditDetailsBinding
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                binding.editDetailsAccountProfilePictureImageView.setImageURI((it.data?.data))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditDetailsBinding.inflate(inflater, container, false)

        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility =
            View.INVISIBLE

        with(binding) {
            editDetailsSelectPhotoBtn.setOnClickListener() { selectImg() }
            editDetailsSaveBtn.setOnClickListener() { apply() }
        }

        return binding.root
    }

    private fun selectImg() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun apply() {
        if (true) {
            toast("Saved")
        }
    }
}