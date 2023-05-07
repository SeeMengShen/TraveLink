package my.edu.tarc.travelink.ui.home.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentScheduleBinding
import my.edu.tarc.travelink.ui.home.data.SearchResult
import java.util.*
import kotlin.collections.ArrayList



class ScheduleFragment : Fragment() {

    private lateinit var adapter: ScheduleSearchAdapter
    private lateinit var viewModel: ScheduleViewModel
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

        viewModel = ViewModelProvider(requireActivity()).get(ScheduleViewModel::class.java)
        adapter = ScheduleSearchAdapter(viewModel)

        binding.scheduleRecycleView.adapter = adapter
        binding.scheduleRecycleView.layoutManager = LinearLayoutManager(requireContext())

        binding.scheduleSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterItems(newText?:"")
                return true
            }
        })

        viewModel.filteredItems.observe(viewLifecycleOwner) { items ->
            adapter.setItems(items)
        }

        adapter.onItemClickListener = object : ScheduleSearchAdapter.OnItemClickListener {
            override fun onItemClick(searchResult: SearchResult) {
                val action = findNavController()
                action.navigate(R.id.scheduleItemDetailFragment, bundleOf("name" to searchResult.name))
            }
        }


    }
}