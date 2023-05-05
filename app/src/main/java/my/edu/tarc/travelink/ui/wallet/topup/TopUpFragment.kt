package my.edu.tarc.travelink.ui.wallet.topup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.databinding.FragmentTopUpBinding
import my.edu.tarc.travelink.ui.login.data.CURRENT_USER

class TopUpFragment : Fragment() {


    private lateinit var binding: FragmentTopUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentTopUpBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            topUpbuttonRM10.setOnClickListener {
                topUpAmountEditTextNumber.setText("%.2f".format(10.0f))
            }
            topUpbuttonRM20.setOnClickListener {
                topUpAmountEditTextNumber.setText("%.2f".format(20.0f))
            }
            topUpbuttonRM50.setOnClickListener {
                topUpAmountEditTextNumber.setText("%.2f".format(50.0f))
            }
            topUpbuttonRM100.setOnClickListener {
                topUpAmountEditTextNumber.setText("%.2f".format(100.0f))
            }
            topUpbuttonRM200.setOnClickListener {
                topUpAmountEditTextNumber.setText("%.2f".format(200.0f))
            }
            topUpbuttonRM300.setOnClickListener {
                topUpAmountEditTextNumber.setText("%.2f".format(300.0f))
            }

            topUpbuttonTopUp.setOnClickListener {
                val inputBalance = topUpAmountEditTextNumber.text
                if (inputBalance.isNullOrEmpty() || inputBalance.toString().toFloat() < 10f) {
                    toast("Minimum reload amount is RM 10!!")
                } else {
                    Firebase.firestore.collection("users").document(CURRENT_USER.value!!.email)
                        .update(
                            "balance",
                            CURRENT_USER.value!!.balance + inputBalance.toString().toFloat()
                        )

                    toast("RM %.2f topped up!".format(inputBalance.toString().toFloat()))
                    findNavController().navigateUp()
                }


            }
        }

    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}