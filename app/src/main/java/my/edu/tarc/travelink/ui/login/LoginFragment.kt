package my.edu.tarc.travelink.ui.login

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.ktx.Firebase
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentForgetPasswordBinding
import my.edu.tarc.travelink.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import my.edu.tarc.travelink.MainActivity
import my.edu.tarc.travelink.ui.account.data.CURRENT_USER
import my.edu.tarc.travelink.ui.account.data.User
import my.edu.tarc.travelink.ui.account.data.UserViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val navController by lazy { findNavController() }
    private val userViewModel :UserViewModel by activityViewModels()

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
                userViewModel.readSeen()

                val intent = Intent(context, MainActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                requireActivity().finish()
                startActivity(intent)
            }
        }
        return binding.root
    }

    private fun login() {
        val email = binding.loginEmailEditText.text.toString()
        val password = binding.loginPasswordEditText.text.toString()

        if(email.isBlank()){
            binding.loginEmailEditText.error = "Email field cannot be empty!"
            return
        }
        else if(password.isBlank()){
            binding.loginPasswordEditText.error = "Password field cannot be empty!"
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener() {
            if (it.isSuccessful) {
                if (auth.currentUser!!.isEmailVerified) {
                    Firebase.firestore.collection("users").document(email).get()
                        .addOnSuccessListener { snap ->
                            CURRENT_USER.value = snap?.toObject()!!
                            userViewModel.readSeen()
                            downloadProfilePicture()

                            val intent = Intent(context, MainActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            requireActivity().finish()
                            toast("Login Successful")
                            startActivity(intent)
                        }
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

    private fun downloadProfilePicture(){
        val imageRef = Firebase.storage.getReferenceFromUrl("gs://travelink-dc333.appspot.com/usersProfilePicture/${CURRENT_USER.value!!.email}")
        val defaultImageRef = Firebase.storage.getReferenceFromUrl("gs://travelink-dc333.appspot.com/usersProfilePicture/travelink_logo.png")

        imageRef.getBytes(1024*1024).addOnSuccessListener { byteArray->
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.count())
            saveProfilePicture(bitmap)
        }.addOnFailureListener {
            defaultImageRef.getBytes(1024*1024).addOnSuccessListener{ defaultByteArray->
                val defaultBitmap = BitmapFactory.decodeByteArray(defaultByteArray, 0, defaultByteArray.count())
                saveProfilePicture(defaultBitmap)
            }
        }
    }

    private fun saveProfilePicture(bitmap: Bitmap) {
        val filename = "profile.jpeg"
        val file = File(this.context?.filesDir, filename)
        val outputStream: OutputStream

        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}