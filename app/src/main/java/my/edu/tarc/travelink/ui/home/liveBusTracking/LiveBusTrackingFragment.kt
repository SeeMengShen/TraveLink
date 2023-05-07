package my.edu.tarc.travelink.ui.home.liveBusTracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import my.edu.tarc.travelink.R


class LiveBusTrackingFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_live_bus_tracking,container,false)
        webView = rootView.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        webView.loadUrl("https://myrapidbus.prasarana.com.my/kiosk?route=858&bus=")
        return rootView
    }
}