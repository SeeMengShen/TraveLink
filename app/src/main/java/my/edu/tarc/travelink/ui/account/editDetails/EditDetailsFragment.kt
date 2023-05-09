package my.edu.tarc.travelink.ui.account.editDetails

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.isDigitsOnly
import androidx.core.text.set
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentAboutUsBinding
import my.edu.tarc.travelink.databinding.FragmentEditDetailsBinding
import my.edu.tarc.travelink.ui.account.data.CURRENT_USER
import my.edu.tarc.travelink.ui.account.data.UserViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class EditDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditDetailsBinding
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                binding.editDetailsAccountProfilePictureImageView.setImageURI((it.data?.data))
            }
        }
    private val userViewModel: UserViewModel by activityViewModels()
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
            editDetailsSaveBtn.setOnClickListener() {
                val name = binding.editDetailsNameEditText.text.toString().trim()
                val phone = binding.editDetailsPhoneNumberEditText.text.toString()
                val gender = when (binding.editDetailsGenderRadioGroup.checkedRadioButtonId) {
                    R.id.editDetailsGenderFemaleRadioBtn -> getString(R.string.editDetailsGenderFemale)
                    else -> getString(R.string.editDetailsGenderMale)
                }
                val malaysian =
                    when (binding.editDetailsNationailityRadioGroup.checkedRadioButtonId) {
                        R.id.editDetailsNationalityMalaysian -> true
                        else -> false
                    }
                val idNum = binding.editDetailsIDNumberEditText.text.toString().trim()

                if (name == "") {
                    toast("Don't leave your name blank!")
                } else {
                    saveProfilePicture(binding.editDetailsAccountProfilePictureImageView)

                    lifecycleScope.launch {
                        uploadProfilePicture()
                        userViewModel.updateUser(name, phone, gender, malaysian, idNum)
                    }.invokeOnCompletion {
                        nav.navigateUp()
                        toast("Updated successfully!")
                    }
                }
            }
        }

        return binding.root
    }

    private fun selectImg() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun saveProfilePicture(view: View) {
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)
        val image = view as ImageView

        val bd = image.drawable as BitmapDrawable
        val bitmap = bd.bitmap
        val outputStream: OutputStream

        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun readProfilePicture(): Bitmap? {
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)

        if (file.isFile) {
            try {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                return bitmap
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        return null
    }

    private fun uploadProfilePicture() {
        val filename = "profile.png"
        val file = Uri.fromFile(File(this.context?.filesDir, filename))

        try {
            val storageRef = Firebase.storage("gs://travelink-dc333.appspot.com").reference

            val profilePictureRef =
                storageRef.child("usersProfilePicture").child(CURRENT_USER.value!!.email)
            profilePictureRef.putFile(file)

        } catch (ex: FileNotFoundException) {
            Log.d("EditDetailsFragment", "Profile picture not found")
        }

    }

    private fun loadUser() {
        // Get existing user data to fill in the edit text field

        val image = readProfilePicture()
        with(binding) {

            if (image != null) {
                binding.editDetailsAccountProfilePictureImageView.setImageBitmap(image)
            } else {
                binding.editDetailsAccountProfilePictureImageView.setImageResource(R.drawable.travelink_logo)
            }

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

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}