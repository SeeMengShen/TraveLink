package my.edu.tarc.travelink.ui.home.schedule

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import my.edu.tarc.travelink.databinding.FragmentScheduleItemDetailBinding
import my.edu.tarc.travelink.ui.home.data.Schedule
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream


class ScheduleItemDetailFragment : Fragment() {

    private lateinit var binding: FragmentScheduleItemDetailBinding
    private val svm: ScheduleViewModel by activityViewModels()
    private val name by lazy { arguments?.getString("name", "") ?: "" }
    private val nav by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScheduleItemDetailBinding.inflate(inflater, container, false)

        val schedule = svm.get(name)

        if (schedule == null) {
            toast("Failed to load the news, please try again")
            nav.navigateUp()
        } else {
            load(schedule)
        }
        return binding.root
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun load(scheduleName: Schedule) {
        scheduleName.route?.let {binding.scheduleItemDetailImage.setImageBitmap(it)}
        scheduleName.timetable?.let {binding.scheduleItemDetailImage2.setImageBitmap(it)}
        binding.scheduleItemDetailName.text = scheduleName.scheduleTitle
    }
}