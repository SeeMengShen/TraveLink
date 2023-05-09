package my.edu.tarc.travelink.ui.home.news

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import my.edu.tarc.travelink.ui.home.data.DBNews
import my.edu.tarc.travelink.ui.home.data.News


class NewsViewModel: ViewModel() {

    val newsList = MutableLiveData<MutableList<News>>()

    companion object {
        val database =
            Firebase.database("https://travelink-dc333-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    }

    init {
        downloadNews()
    }

    fun get(newsTitle: String): News? {
        return newsList.value!!.find{news -> news.newsTitle == newsTitle}
    }

    fun getAll() = newsList.value

    @WorkerThread
    fun downloadNews() = viewModelScope.launch{
        database.child("news").get()
            .addOnSuccessListener {

                if (!it.exists()) {
                    return@addOnSuccessListener
                }

                // Convert snapshot to list
                val dbNewsList = it.getValue<List<DBNews>>()
                newsList.value = dbNewstoNews(dbNewsList!!)
            }
    }

    // Convert the DBNews to News
    private fun dbNewstoNews(dbNewsList : List<DBNews>): MutableList<News>{
        val newNewsList: MutableList<News> = mutableListOf()
        dbNewsList.forEachIndexed { index, dbNews ->
            newNewsList.add(index, News(index, dbNews.newsTitle, dbNews.newsDate, dbNews.newsDesc, ))
        }

        return newNewsList
    }
}


