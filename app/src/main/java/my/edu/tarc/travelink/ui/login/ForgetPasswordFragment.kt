package my.edu.tarc.travelink.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentForgetPasswordBinding

class ForgetPasswordFragment : Fragment() {

    private var _binding: FragmentForgetPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val navController by lazy {findNavController()}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        with(binding){
            forgetPasswordSendEmailButton.setOnClickListener() {forgetPassword()}
        }

        return binding.root
    }
    private fun forgetPassword(){
        val userEmail = binding.editTextEmailAddress.text.toString()

        if(userEmail.isBlank()){
            //toast("Email field cannot be blank.")
            binding.editTextEmailAddress.error = "Email cannot be empty!"
            return
        }

        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener{
            if(it.isSuccessful){
                toast("A password reset request has been sent to your email")
                navController.navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun toast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}