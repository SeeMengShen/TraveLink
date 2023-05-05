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
import androidx.core.text.set
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentAboutUsBinding
import my.edu.tarc.travelink.databinding.FragmentEditDetailsBinding
import my.edu.tarc.travelink.ui.account.data.UserViewModel
import my.edu.tarc.travelink.ui.login.data.CURRENT_USER
import my.edu.tarc.travelink.ui.login.data.User

class EditDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditDetailsBinding
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                binding.editDetailsAccountProfilePictureImageView.setImageURI((it.data?.data))
            }
        }
    private val uvm: UserViewModel by activityViewModels()
    private val nav by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditDetailsBinding.inflate(inflater, container, false)

        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility =
            View.INVISIBLE

        loadUser()

        with(binding) {
            editDetailsSelectPhotoBtn.setOnClickListener() { selectImg() }
            editDetailsSaveBtn.setOnClickListener() { updateUserInfo() }
        }

        return binding.root
    }

    private fun selectImg() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun loadUser() {
        with(binding) {
            editDetailsNameEditText.setText(CURRENT_USER.value!!.name)
            editDetailsPhoneNumberEditText.setText(CURRENT_USER.value!!.phone)
            editDetailsGenderRadioGroup.check(
                when (CURRENT_USER.value!!.gender) {
                    getString(R.string.editDetailsGenderFemale) -> R.id.editDetailsGenderFemaleRadioBtn
                    else -> R.id.editDetailsGenderMaleRadioBtn
                }
            )
            editDetailsNationailityRadioGroup.check(
                if (CURRENT_USER.value!!.malaysian) {
                    R.id.editDetailsNationalityMalaysian
                } else {
                    R.id.editDetailsNationalityNonMalaysian
                }
            )
            editDetailsIDNumberEditText.setText(CURRENT_USER.value!!.idNum)
        }
    }

    private fun updateUserInfo() {
        val firebaseUser = Firebase.firestore.collection("users").document(CURRENT_USER.value!!.email)

        val user = User(
            email = CURRENT_USER.value!!.email,
            name = binding.editDetailsNameEditText.text.toString().trim(),
            phone = binding.editDetailsPhoneNumberEditText.text.toString().trim(),
            gender = when (binding.editDetailsGenderRadioGroup.checkedRadioButtonId) {
                R.id.editDetailsGenderFemaleRadioBtn -> getString(R.string.editDetailsGenderFemale)
                else -> getString(R.string.editDetailsGenderMale)
            },
            malaysian = when (binding.editDetailsNationailityRadioGroup.checkedRadioButtonId) {
                R.id.editDetailsNationalityMalaysian -> true
                else -> false
            },
            idNum = binding.editDetailsIDNumberEditText.text.toString().trim()
        )

        lifecycleScope.launch {
            val err = uvm.validate(user)

            if (err != "") {
                toast(err)
                return@launch
            }

            CURRENT_USER.value = user
            firebaseUser.set(user).addOnSuccessListener {
                nav.navigateUp()
                toast("Updated successfully!")
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}