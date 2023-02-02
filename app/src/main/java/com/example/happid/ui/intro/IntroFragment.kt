package com.example.happid.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.happid.R
import com.example.happid.databinding.FragmentIntroBinding
import com.example.happid.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : Fragment(){

    private var binding: FragmentIntroBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(
                R.id.action_introFragment_to_loginFragment
            )
        }
    }
}
