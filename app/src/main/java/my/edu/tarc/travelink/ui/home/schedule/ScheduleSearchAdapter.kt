package my.edu.tarc.travelink.ui.home.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.travelink.databinding.FragmentScheduleItemBinding
import my.edu.tarc.travelink.ui.home.data.SearchResult

import my.edu.tarc.travelink.ui.home.news.NewsAdapter


class ScheduleSearchAdapter(private val viewModel: ScheduleViewModel):
    RecyclerView.Adapter<ScheduleSearchAdapter.ViewHolder>(){


    private var items = emptyList<SearchResult>()


    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentScheduleItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.scheduleItemText.text = item.name


        // Set up the item click listener
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<SearchResult>) {
        this.items = items
        notifyDataSetChanged()
    }


    interface OnItemClickListener {
        fun onItemClick(searchResult: SearchResult)
    }

    inner class ViewHolder(val binding: FragmentScheduleItemBinding) : RecyclerView.ViewHolder(binding.root)


}






