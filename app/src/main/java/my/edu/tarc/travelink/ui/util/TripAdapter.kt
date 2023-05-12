package my.edu.tarc.travelink.ui.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.ui.wallet.data.Trip
import my.edu.tarc.travelink.ui.wallet.trip.TripFragment
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class TripAdapter : RecyclerView.Adapter<TripAdapter.ViewHolder>() {
    private var tripList = emptyList<Trip>()

    //declare val to store the textview, and store in the holder in on bind view holder
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val boardingStationTextView: TextView = view.findViewById(R.id.boardingStationTextView)
        val boardingTimeTextView: TextView = view.findViewById(R.id.boardingTimeTextView)
        val dropOffStationTextView: TextView = view.findViewById(R.id.dropOffStationTextView)
        val dropOffTimeTextView: TextView = view.findViewById(R.id.dropOffTimeTextView)
        val fareTextView: TextView = view.findViewById(R.id.fareTextView)
    }

    internal fun setTrips(trips: List<Trip>) {
        this.tripList = trips
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripAdapter.ViewHolder {
        //Create a new view, which define the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripAdapter.ViewHolder, position: Int) {

        holder.boardingStationTextView.text = tripList[position].boardingStation
        holder.boardingTimeTextView.text =tripList[position].boardingDateTimeToString()

        if (tripList[position].isBoarding()) {
            holder.dropOffStationTextView.text = "-"
            holder.dropOffTimeTextView.text = "-"
            holder.fareTextView.text = ""
        } else {
            holder.dropOffStationTextView.text = tripList[position].dropOffStation
            holder.dropOffTimeTextView.text = tripList[position].dropOffDateTimeToString()
            holder.fareTextView.text = String.format("Fare: RM %.2f", tripList[position].fare)
        }
    }

    override fun getItemCount(): Int {
        return tripList.size
    }


}