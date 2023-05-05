package my.edu.tarc.travelink.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.ktx.Firebase
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentForgetPasswordBinding
import my.edu.tarc.travelink.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import my.edu.tarc.travelink.MainActivity
import my.edu.tarc.travelink.ui.login.data.CURRENT_USER
import my.edu.tarc.travelink.ui.login.data.User

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        with(binding) {
            loginForgetPasswordButton.setOnClickListener() { navController.navigate(R.id.forgetPasswordFragment) }
            loginLoginButton.setOnClickListener() { login() }
            loginRegisterButton.setOnClickListener() { navController.navigate(R.id.registerFragment) }
        }

        if (auth.currentUser != null) {
            val firebaseUser =
                Firebase.firestore.collection("users").document(auth.currentUser!!.email.toString())
            firebaseUser.get().addOnSuccessListener { snap ->
                CURRENT_USER.value = snap?.toObject()!!

                Firebase.firestore.collection("users").document(CURRENT_USER.value!!.email)
                    .addSnapshotListener() { snap, _ ->
                        CURRENT_USER.value = snap?.toObject(User::class.java)!!
                    }
            }

            val intent = Intent(context, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            requireActivity().finish()

            startActivity(intent)
        }

        return binding.root
    }

    private fun login() {
        val email = binding.loginEmailEditText.text.toString()
        val password = binding.loginPasswordEditText.text.toString()

        if (email.isBlank() || password.isBlank()) {
            toast("Email and Password can't be blank")
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener() {
            if (it.isSuccessful) {
                if (auth.currentUser!!.isEmailVerified) {
                    toast("Login Successful")
                    Firebase.firestore.collection("users").document(email).get()
                        .addOnSuccessListener { snap ->
                            CURRENT_USER.value = snap?.toObject()!!

                            Firebase.firestore.collection("users").document(CURRENT_USER.value!!.email)
                                .addSnapshotListener() { snap, _ ->
                                    CURRENT_USER.value = snap?.toObject(User::class.java)!!
                                }
                        }

                    val intent = Intent(context, MainActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    requireActivity().finish()

                    startActivity(intent)

                } else {
                    toast("Please verify your email address")
                }
            } else {
                toast("Invalid Login Detail")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}