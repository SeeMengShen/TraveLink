package my.edu.tarc.travelink.ui.home.news

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.tabs.TabLayout
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentNewsBinding
import my.edu.tarc.travelink.ui.home.data.News
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val nav by lazy { findNavController() }
    private val nvm: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        val adapter = NewsAdapter { holder, news ->
            holder.root.setOnClickListener {
                nav.navigate(R.id.newsDetailFragment, bundleOf("newsTitle" to news.newsTitle))
            }
        }

        binding.newsRv.adapter = adapter
        binding.newsRv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        // Set observer to newsList for updating the photo and resubmit it to recycleView adapter
        nvm.newsList.observe(viewLifecycleOwner) { newsList ->

            newsList.forEachIndexed { index, news ->
                news.newsPhoto = readNewsPicture(index)
                if (news.newsPhoto == null) {
                    downloadNewsPicture(index, adapter)
                }
            }

            adapter.submitList(newsList)

            // Reset the swipeRefresh
            binding.newsSwipeRefresh.isRefreshing = false
        }

        // Sync the news from database when swipe refresh
        binding.newsSwipeRefresh.setOnRefreshListener {
            nvm.downloadNews()
        }

        return binding.root
    }

    private fun downloadNewsPicture(index: Int, adapter: NewsAdapter) {
        val imageRef =
            Firebase.storage.getReferenceFromUrl("gs://travelink-dc333.appspot.com/newsPhoto/$index.png")

        imageRef.getBytes(1024 * 1024).addOnSuccessListener { byteArray ->
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.count())
            saveNewsPicture(bitmap, index)
            nvm.newsList.value!![index].newsPhoto = readNewsPicture(index)
            adapter.notifyItemChanged(index)
        }
    }

    private fun saveNewsPicture(bitmap: Bitmap, newsID: Int) {
        val filename = "$newsID.jpeg"
        val file = File(this.context?.filesDir, filename)
        /*val image = view as ImageView

        val bd = image.drawable as BitmapDrawable
        val bitmap = bd.bitmap*/
        val outputStream: OutputStream

        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 1, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun readNewsPicture(index: Int): Bitmap? {
        val filename = "$index.jpeg"
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