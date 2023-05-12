package my.edu.tarc.travelink.ui.account

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import my.edu.tarc.travelink.LoginActivity
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.ActivityMainBinding
import my.edu.tarc.travelink.databinding.FragmentAccountBinding
import my.edu.tarc.travelink.ui.account.data.CURRENT_USER
import my.edu.tarc.travelink.ui.account.data.User
import my.edu.tarc.travelink.ui.account.data.UserViewModel
import my.edu.tarc.travelink.ui.wallet.data.TripViewModel
import java.io.File
import java.io.FileNotFoundException

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var auth: FirebaseAuth
    private val tripViewModel : TripViewModel by activityViewModels()
    private val userViewModel : UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        val navController = findNavController()
        val image = readProfilePicture()
        with(binding) {

            if (image != null) {
                binding.accountProfilePictureImageView.setImageBitmap(image)
            } else {
                binding.accountProfilePictureImageView.setImageResource(R.drawable.travelink_logo)
            }

            // Read values from CURRENT_USER
            accountNameTextView.text = CURRENT_USER.value!!.name
            accountEmailTextView.text = CURRENT_USER.value!!.email

            accountChangePasswordBtn.setOnClickListener() { promptDialog("Confirm to reset password?", 1) }
            accountEditDetailsBtn.setOnClickListener() { navController.navigate(R.id.editDetailsFragment) }
            accountFeedbackBtn.setOnClickListener() { navController.navigate(R.id.feedbackFragment) }
            accountAboutUsBtn.setOnClickListener() { navController.navigate(R.id.aboutUsFragment)}
            accountLogoutBtn.setOnClickListener() { promptDialog("Confirm to log out?", 2) }
        }

        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE

        return binding.root
    }

    // Build a Alert Dialog Box depending on the type, 1 for resetPassword 2 for logout
    private fun promptDialog(text: String, type: Int) {
        AlertDialog.Builder(context)
            .setIcon(R.drawable.baseline_error_outline_24)
            .setTitle("Confirmation").setMessage(text)
            .setPositiveButton("Confirm") { dialog, which ->
                when (type) {
                    2 -> logout()
                    1 -> resetPwd()

                    else -> Unit
                }
            }
            .setNegativeButton("Cancel", null).show()
    }

    private fun resetPwd() {
        auth.sendPasswordResetEmail(CURRENT_USER.value!!.email)
        toast("A password reset request has been send to your email. Please rest your password and login again.")

        logout()
    }

    private fun logout() {
        // Clear off the trip from the ViewModel and local database
        tripViewModel.clearTrip()

        // Sign out and call LoginActivity
        auth.signOut()

        val intent = Intent(context, LoginActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireActivity().finish()

        startActivity(intent)

        // Reset the CURRENT_USER
        CURRENT_USER.value = User()
        toast("Log out successfully!")
    }

    private fun readProfilePicture(): Bitmap? {
        val filename = "profile.jpeg"
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

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}