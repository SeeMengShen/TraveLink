package my.edu.tarc.travelink.ui.scan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentScanQrCodeBinding
import my.edu.tarc.travelink.ui.wallet.data.Trip
import my.edu.tarc.travelink.ui.wallet.data.TripViewModel
import org.json.JSONObject
import java.lang.reflect.InvocationTargetException
import java.time.LocalDateTime

class ScanQRCodeFragment : Fragment() {
    private val tripViewModel: TripViewModel by activityViewModels()
    private lateinit var binding: FragmentScanQrCodeBinding
    private val nav by lazy { findNavController() }
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted ->
        if(isGranted){
            Toast.makeText(requireContext(),"Permission Granted",Toast.LENGTH_SHORT).show()
            startScanning()
        }else{
            Toast.makeText(requireContext(),"Permission Not Granted",Toast.LENGTH_SHORT).show()
        }
    }


    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScanQrCodeBinding.inflate(inflater, container, false)

        if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ){
            requestPermission.launch(Manifest.permission.CAMERA)
        }else{
            startScanning()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun startScanning() {
        codeScanner = CodeScanner(this.requireContext(), binding.scanScannerView)

        with(codeScanner) {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            codeScanner.scanMode = ScanMode.SINGLE
            isFlashEnabled = false
            codeScanner.decodeCallback = DecodeCallback {

                requireActivity().runOnUiThread {
                    if(qrCodeFound(it)){
                        //go to successful fragment
                        findNavController().navigate(R.id.scanSuccessfulFragment)
                    }else{
                        startPreview()
                    }

                    //Toast.makeText(requireActivity(), "Result: ${it.text}",Toast.LENGTH_SHORT).show()

                }
            }



            errorCallback = ErrorCallback {
                requireActivity().runOnUiThread{
                    Toast.makeText(requireActivity(), "Error: ${it.message}",Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

    private fun qrCodeFound(it: com.google.zxing.Result): Boolean {

        var jsonObject: JSONObject

        //check is json or not
        try {
            //convert string to json
            jsonObject = JSONObject(it.text)
        }catch (ex : Exception){
            Toast.makeText(this.requireActivity(),"Invalid QR code detected!",Toast.LENGTH_LONG).show()

            return false
        }


        //check valid json or not
        if(!jsonObject.has("isTravelink")){
            Toast.makeText(this.requireActivity(),"Invalid QR code detected!",Toast.LENGTH_LONG).show()
            return false
        }

        //yes, valid qrcode and continue
        val stationID = jsonObject.getString("stationID")
        val stationName = jsonObject.getString("stationName")





        //check previously got trip or not
        val lastTrip = tripViewModel.tripHistory.value?.lastOrNull()

        if(lastTrip?.isBoarding() == true){
            tripViewModel.currentTrip = lastTrip
        }

        //check is boarding or not, if yes create a new trip
        if(tripViewModel.currentTrip == null){
            tripViewModel.currentTrip = Trip(
                boardingStationID = stationID,
                boardingStation = stationName,
                boardingDateTime = LocalDateTime.now()
            )

        }
        //if drop off, add the drop off info
        else{
            with(tripViewModel.currentTrip!!){
                dropOffStation = stationName
                dropOffStationID = stationID
                dropOffDateTime = LocalDateTime.now()
                fare = 3.0f
            }

        }


        return true
    }

    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized){
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {

        if(::codeScanner.isInitialized){
            codeScanner?.releaseResources()
        }
        super.onPause()
    }

}