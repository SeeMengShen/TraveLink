package my.edu.tarc.travelink.ui.home.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentNewsDetailBinding
import my.edu.tarc.travelink.ui.home.data.News
import java.time.format.DateTimeFormatter

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding
    private val nav by lazy{ findNavController()}
    private val newsTitle by lazy{ arguments?.getString("newsTitle","")?:""}
    private val nvm: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentNewsDetailBinding.inflate(inflater, container,false)

        val news = nvm.get(newsTitle)

        if(news == null) {
            toast("Failed to load the news, please try again")
            nav.navigateUp()
        }
        else{
            load(news)
        }

        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.INVISIBLE
        return binding.root
    }

    private fun toast(text: String){
        Toast.makeText(context,text, Toast.LENGTH_SHORT).show()
    }

    private fun load(news: News){
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        news.newsPhoto?.let {binding.newsDetailImage.setImageResource(it)}
        binding.newsDetailTitle.text = news.newsTitle
        binding.newsDetailDate.text = formatter.format(news.newsDate)
        binding.newsDetailDesc.text = news.newsDesc

    }
}