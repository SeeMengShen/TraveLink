package my.edu.tarc.travelink.ui.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.edu.tarc.travelink.ui.account.data.User

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val navController by lazy { findNavController() }
    private val firebaseUser = Firebase.firestore.collection("users")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        with(binding) {
            registerLoginButton.setOnClickListener() { navController.navigateUp() }
            registerRegisterButton.setOnClickListener() { register() }
        }

        return binding.root
    }

    private fun register() {
        val userName = binding.registerNameEditText.text.toString()
        val userEmail = binding.registerEmailEditText.text.toString()
        val userPassword = binding.registerPasswordEditText.text.toString()
        val userConfirmPassword = binding.registerConfirmPasswordEditText.text.toString()
        val userTnCCheckbox = binding.registerTnCCheckbox

        if(userName.isBlank()){
            binding.registerNameEditText.error = "Username cannot be empty!"
            return
        }
        else if(userEmail.isBlank()){
            binding.registerEmailEditText.error = "Email cannot be empty!"
            return
        }
        else if(userPassword.isBlank()){
            binding.registerPasswordEditText.error = "Password cannot be empty!"
            return
        }
        else if(userConfirmPassword.isBlank()){
            binding.registerConfirmPasswordEditText.error = "Confirm Password cannot be empty!"
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
        {
            binding.registerEmailEditText.error = "Invalid email address!"
            return
        }

        if(userPassword.length < 6){
            binding.registerPasswordEditText.error = "Password cannot be shorter than 6 characters!"
            return
        }

        if(userPassword != userConfirmPassword){
            //toast("Confirm Password does not match with Password.")
            binding.registerConfirmPasswordEditText.error = "Does not match with Password!"
            return
        }

        if (!userTnCCheckbox.isChecked) {
            toast("Please check on the Terms and Condition before proceeding.")
            return
        }

        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
            val user = auth.currentUser

            if (task.isComplete) {
                user!!.sendEmailVerification().addOnCompleteListener {
                    if (task.isSuccessful) {
                        toast("Registration Successful, please check yor email address for verification!")
                        writeNewUser(userEmail, userName)
                        auth.signOut()
                        navController.navigateUp()
                    } else {
                        Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun writeNewUser(userEmail: String, userName: String) {
        val newUser = User(
            userEmail,
            userName,
            "",
            "",
            false,
            "",
            0f,
            emptyList()
        )

        firebaseUser.document(userEmail).set(newUser)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}