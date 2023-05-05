package my.edu.tarc.travelink.ui.account

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import my.edu.tarc.travelink.LoginActivity
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.ActivityMainBinding
import my.edu.tarc.travelink.databinding.FragmentAccountBinding
import my.edu.tarc.travelink.ui.login.data.CURRENT_USER
import my.edu.tarc.travelink.ui.login.data.User

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        val navController = findNavController()

        with(binding) {
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
        auth.signOut()

        val intent = Intent(context, LoginActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireActivity().finish()

        startActivity(intent)

        CURRENT_USER.value = User()
        toast("Log out successfully!")
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}