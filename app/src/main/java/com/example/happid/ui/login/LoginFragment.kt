package com.example.happid.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.happid.R
import com.example.happid.databinding.FragmentLoginBinding
import com.example.happid.utils.alert
import com.example.happid.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), OnClickListener {

    private var binding: FragmentLoginBinding by autoCleared()
    var count = 0
    var otpCode = "0"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupListener()


    }

    private fun initView() {

        binding.edtMobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputLength = binding.edtMobileNumber.text.toString().length

                with(binding.edtMobileNumber) {

                    if (count <= inputLength && inputLength == 5) {
                        this.setText(this.text.toString().plus(" "))
                        this.setSelection(this.text.toString().length)
                    } else if (count >= inputLength && inputLength == 5) {
                        this.setText(this.toString().substring(0, this.length() - 1))
                        this.setSelection(this.text.toString().length)
                    }

                    count = this.text.toString().length

                }
            }
        })
    }

    private fun setupListener() {
        binding.btnRequestOtp.setOnClickListener(this)
        binding.tvOkay.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btn_request_otp -> {
                if (validate()) {
                    otpCode = "${
                        binding.edtMobileNumber.text.toString().substring(0, 2)
                    }${binding.edtMobileNumber.text.toString().substring(9, 11)}"
                    binding.tvCode.text = otpCode
                    binding.cardDialogCode.visibility = View.VISIBLE
                }
            }

            R.id.tv_okay -> {
                binding.cardDialogCode.visibility = View.GONE

                val bundle = Bundle()
                bundle.putString("code", otpCode)
                bundle.putString("number", binding.edtMobileNumber.text.toString().trim())

                findNavController().navigate(
                    R.id.action_loginFragment_to_OTPVerificationFragment,
                    bundle
                )

            }
        }
    }

    fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.edtMobileNumber.text.toString())) {
            alert("Please enter mobile number")
            return false
        } else if (binding.edtMobileNumber.text.toString().length < 11) {
            alert("Please enter valid mobile number")
            return false
        }
        return true
    }
}
