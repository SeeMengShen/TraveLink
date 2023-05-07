package my.edu.tarc.travelink.ui.account.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentAboutUsBinding
import my.edu.tarc.travelink.databinding.FragmentFeedbackBinding
import java.util.*
import kotlin.collections.HashMap

class FeedbackFragment : Fragment() {

    private lateinit var binding: FragmentFeedbackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)

        binding.feedbackSubmitBtn.setOnClickListener() { submit() }

        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.INVISIBLE

        return binding.root
    }

    private fun submit() {

        if (validate()) {
            with(binding) {

                val category = when(feedbackCategorySpinner.selectedItemPosition){
                    0 -> "Bus service"
                    1 -> "Application"
                    2 -> "Top up issue"
                    else -> "Others"
                }

                val feedback = hashMapOf(
                    "Category" to category,
                    "Subject" to feedbackSubjectEditText.text.toString(),
                    "Date" to Date(),
                    "Message" to feedbackMessage.text.toString()
                )

                Firebase.firestore.collection("feedbacks").document().set(feedback).addOnSuccessListener {
                    toast("Submitted! Thanks for your feedback!")
                    findNavController().navigateUp()
                }
            }
        }

    }

    private fun validate(): Boolean {
        if (binding.feedbackSubjectEditText.text.trim().isEmpty()) {
            toast("Subject should not be empty!")
            return false
        } else if (binding.feedbackMessage.text?.trim()?.isEmpty() == true) {
            toast("Message should not be empty!")
            return false
        }

        return true
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}