package my.edu.tarc.travelink.ui.home.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentNewsDetailBinding
import my.edu.tarc.travelink.ui.account.data.CURRENT_USER
import my.edu.tarc.travelink.ui.account.data.User
import my.edu.tarc.travelink.ui.account.data.UserViewModel
import my.edu.tarc.travelink.ui.home.data.News
import java.time.format.DateTimeFormatter

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding
    private val nav by lazy { findNavController() }
    private val newsTitle by lazy { arguments?.getString("newsTitle", "") ?: "" }
    private val nvm: NewsViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    var currentViewingNews: News? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)

        // Read the news object according to the newsTitle
        currentViewingNews = nvm.get(newsTitle)

        if (currentViewingNews == null) {
            toast("Failed to load the news, please try again")
            nav.navigateUp()
        } else {
            load(currentViewingNews!!)
        }

        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility =
            View.INVISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var seen = false

        if (CURRENT_USER.value!!.seenPost == emptyList<Int>()) {
            seen = false
        } else if(CURRENT_USER.value!!.seenPost.contains(currentViewingNews!!.newsID)) {
            seen = true
        }

        if(!seen){
            lifecycleScope.launch {
                userViewModel.updateSeen(currentViewingNews!!.newsID)
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun load(news: News) {
        news.newsPhoto?.let { binding.newsDetailImage.setImageBitmap(it) }
        binding.newsDetailTitle.text = news.newsTitle
        binding.newsDetailDate.text = news.newsDate
        binding.newsDetailDesc.text = news.newsDesc

    }
}