package my.edu.tarc.travelink

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import my.edu.tarc.travelink.databinding.ActivityMainBinding
import my.edu.tarc.travelink.ui.util.TripAdapter
import my.edu.tarc.travelink.ui.wallet.data.Trip
import my.edu.tarc.travelink.ui.wallet.data.TripViewModel
import kotlin.text.Typography.dagger

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var tripViewModel: TripViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //-------------------trip------------------------------
        tripViewModel = ViewModelProvider(this,
        ).get(TripViewModel::class.java)

        val adapter = TripAdapter()

        tripViewModel.tripHistory.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.setTrips(it)
            }
        })


        //---------------nav------------------
        val navView: BottomNavigationView = binding.navView

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.walletFragment, R.id.accountFragment
            )
        )
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        //-------------------fab---------------------
        binding.fabScan.setOnClickListener {
            navController.navigate(R.id.scanQRCodeFragment)
        }



        //disable the camera fab and nav view (3 bottom) here
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.fabScan.visibility = when (destination.id) {
                R.id.homeFragment, R.id.walletFragment, R.id.accountFragment -> View.VISIBLE
                    else -> View.INVISIBLE
            }
            binding.navView.visibility =binding.fabScan.visibility

        }





    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}