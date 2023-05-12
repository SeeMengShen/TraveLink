package my.edu.tarc.travelink.ui.home.schedule

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentScheduleBinding
import my.edu.tarc.travelink.ui.home.data.Schedule
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream


class ScheduleFragment : Fragment() {

    private lateinit var adapter: ScheduleSearchAdapter
    private val svm: ScheduleViewModel by activityViewModels()
    private lateinit var binding: FragmentScheduleBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ScheduleSearchAdapter(svm)

        binding.scheduleRecycleView.adapter = adapter
        binding.scheduleRecycleView.layoutManager = LinearLayoutManager(requireContext())

        binding.scheduleSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                svm.filterItems(newText?:"")
                return true
            }
        })

        svm.filteredItems?.observe(viewLifecycleOwner) { items ->
            if(items != null){

                svm.filteredItems?.value!!.forEachIndexed { index, schedule ->
                    schedule.route = readSchedulePicture(index, "r")
                    schedule.timetable = readSchedulePicture(index, "t")

                    if(schedule.route == null || schedule.timetable == null){
                        downloadSchedulePicture(index)
                    }
                }

                adapter.setItems(items)
            }
        }

        adapter.onItemClickListener = object : ScheduleSearchAdapter.OnItemClickListener {
            override fun onItemClick(schedule: Schedule) {
                val action = findNavController()
                action.navigate(R.id.scheduleItemDetailFragment, bundleOf("name" to schedule.scheduleTitle))
            }
        }

    }

    private fun downloadSchedulePicture(index: Int) {
        val routeImageRef =
            Firebase.storage.getReferenceFromUrl("gs://travelink-dc333.appspot.com/schedulePhoto/r$index.png")
        val timetableImageRef =
            Firebase.storage.getReferenceFromUrl("gs://travelink-dc333.appspot.com/schedulePhoto/t$index.png")

        routeImageRef.getBytes(1024 * 1024).addOnSuccessListener { byteArray ->
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.count())
            saveSchedulePicture(bitmap, index, "r")
            svm.filteredItems?.value!![index].route = readSchedulePicture(index, "r")
            adapter.notifyItemChanged(index)
        }

        timetableImageRef.getBytes(1024 * 1024).addOnSuccessListener { byteArray ->
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.count())
            saveSchedulePicture(bitmap, index, "t")
            svm.filteredItems?.value!![index].timetable = readSchedulePicture(index, "t")
            adapter.notifyItemChanged(index)
        }
    }

    private fun saveSchedulePicture(bitmap: Bitmap, schduleID: Int, prefix: String) {
        val filename = "$prefix$schduleID.png"
        val file = File(this.context?.filesDir, filename)
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

    private fun readSchedulePicture(index: Int, prefix: String): Bitmap? {
        val filename = "$prefix$index.png"
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
}