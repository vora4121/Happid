package com.example.happid.ui.otpverification

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.happid.R
import com.example.happid.databinding.FragmentOTPVerificationBinding
import com.example.happid.utils.alert

class OTPVerificationFragment : Fragment() {
    val binding: FragmentOTPVerificationBinding by lazy {
        FragmentOTPVerificationBinding.inflate(
            layoutInflater
        )
    }
    var otpCode = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("code")?.let { otpCode = it }
        arguments?.getString("number")?.let {
            binding.tvMobileNumber.text = "+91$it"
        }

        initOtpSetup()

        binding.btnRequestOtp.setOnClickListener {
            with(binding) {
                if (validate()) {
                    if (otpCode == "${this.edt1stOtp.text.toString()}${this.edt2ndOtp.text.toString()}${this.edt3rdOtp.text.toString()}${this.edt4thOtp.text.toString()}") {
                        findNavController().navigate(
                            R.id.action_OTPVerificationFragment_to_profileFragment2
                        )
                    } else {
                        alert("Please enter valid OTP")
                    }
                }
            }
        }
    }

    private fun initOtpSetup() {
        with(binding) {
            edt1stOtp.addTextChangedListener(GenericTextWatcher(edt1stOtp, edt2ndOtp, null))
            edt2ndOtp.addTextChangedListener(GenericTextWatcher(edt2ndOtp, edt3rdOtp, edt1stOtp))
            edt3rdOtp.addTextChangedListener(GenericTextWatcher(edt3rdOtp, edt4thOtp, edt2ndOtp))
            edt4thOtp.addTextChangedListener(GenericTextWatcher(edt4thOtp, null, edt3rdOtp))

            //GenericKeyEvent here works for deleting the element and to switch back to previous EditText
            //first parameter is the current EditText and second parameter is previous EditText
            edt1stOtp.setOnKeyListener(GenericKeyEvent(edt1stOtp, null))
            edt2ndOtp.setOnKeyListener(GenericKeyEvent(edt2ndOtp, edt1stOtp))
            edt3rdOtp.setOnKeyListener(GenericKeyEvent(edt3rdOtp, edt2ndOtp))
            edt4thOtp.setOnKeyListener(GenericKeyEvent(edt4thOtp, edt3rdOtp))
        }
    }

    class GenericKeyEvent internal constructor(
        private val currentView: EditText,
        private val previousView: EditText?
    ) : View.OnKeyListener {
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.edt_1st_otp && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                previousView.requestFocus()
                return true
            }
            return false
        }
    }

    class GenericTextWatcher internal constructor(
        private val currentView: View,
        private val nextView: View?,
        private val previusView: View?
    ) :
        TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            when (currentView.id) {
                R.id.edt_1st_otp -> if (text.length == 1) nextView!!.requestFocus()
                R.id.edt_2nd_otp -> if (text.length == 1) nextView!!.requestFocus() else if (text.isEmpty()) previusView!!.requestFocus()
                R.id.edt_3rd_otp -> if (text.length == 1) nextView!!.requestFocus() else if (text.isEmpty()) previusView!!.requestFocus()
                R.id.edt_4th_otp -> if (text.isEmpty()) previusView!!.requestFocus()

            }
        }

        override fun beforeTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }

        override fun onTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }
    }

    private fun validate(): Boolean {

        with(binding) {
            if (TextUtils.isEmpty(this.edt1stOtp.text.toString()) ||
                TextUtils.isEmpty(this.edt2ndOtp.text.toString()) ||
                TextUtils.isEmpty(this.edt3rdOtp.text.toString()) ||
                TextUtils.isEmpty(this.edt4thOtp.text.toString())
            ) {
                alert("Please enter OTP")
                return false
            }
        }
        return true
    }

}