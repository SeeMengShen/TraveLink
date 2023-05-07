package my.edu.tarc.travelink.ui.home.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.tabs.TabLayout
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentNewsBinding
import my.edu.tarc.travelink.ui.home.data.News

class NewsFragment : Fragment() {

   private lateinit var binding: FragmentNewsBinding
   private val nav by lazy{findNavController()}
    private val nvm: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        val adapter = NewsAdapter(){ holder,news, ->
            holder.root.setOnClickListener{
                nav.navigate(R.id.newsDetailFragment, bundleOf("newsTitle" to news.newsTitle))
            }
        }

        binding.newsRv.adapter = adapter
        binding.newsRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val newsList = nvm.news.sortedByDescending { news -> news.newsDate }

        adapter.submitList(newsList)

        return binding.root
    }



}