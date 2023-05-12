package my.edu.tarc.travelink.ui.home.news

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.ui.home.data.News
import my.edu.tarc.travelink.ui.wallet.data.Trip
import org.w3c.dom.Text
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class NewsAdapter(val fn:(ViewHolder, News) -> Unit): ListAdapter<News, NewsAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack: DiffUtil.ItemCallback<News>() {

        override fun areItemsTheSame(a: News,b: News) = a.newsTitle == b.newsTitle
        override fun areContentsTheSame(a: News, b:News) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val newsTitleView: TextView = view.findViewById(R.id.newsItemTitleView)
        val newsDateView: TextView = view.findViewById(R.id.newsItemDateView)
        val newsDescView: TextView = view.findViewById(R.id.newsItemDescView)
        val newsImageView: ImageView = view.findViewById(R.id.newsItemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = getItem(position)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        //news.newsPhoto?.let {  holder.newsImageView.setImageResource(it)}
        news.newsPhoto?.let {  holder.newsImageView.setImageBitmap(it)}
        holder.newsTitleView.text = news.newsTitle
        holder.newsDateView.text = /*formatter.format(*/news.newsDate//)
        holder.newsDescView.text = news.newsDesc

        fn(holder, news)

    }
}