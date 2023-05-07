package my.edu.tarc.travelink.ui.home.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentScheduleItemDetailBinding
import my.edu.tarc.travelink.ui.home.data.SearchResult
import java.time.format.DateTimeFormatter


class ScheduleItemDetailFragment : Fragment() {

    private lateinit var binding: FragmentScheduleItemDetailBinding
    private val svm: ScheduleViewModel by activityViewModels()
    private val name by lazy{arguments?.getString("name","")?:""}
    private val nav by lazy{ findNavController()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScheduleItemDetailBinding.inflate(inflater, container, false)

        val schedule = svm.get(name)

        if(schedule == null) {
            toast("Failed to load the news, please try again")
            nav.navigateUp()
        }
        else{
            load(schedule)
        }
        return binding.root
    }

    private fun toast(text: String){
        Toast.makeText(context,text, Toast.LENGTH_SHORT).show()
    }

    private fun load(scheduleName: SearchResult){
        scheduleName.image?.let {binding.scheduleItemDetailImage.setImageResource(it)}
        scheduleName.image2?.let {binding.scheduleItemDetailImage2.setImageResource(it)}
        binding.scheduleItemDetailName.text = scheduleName.name


    }
}