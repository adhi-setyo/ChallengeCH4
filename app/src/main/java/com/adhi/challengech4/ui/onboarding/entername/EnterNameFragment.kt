package com.adhi.challengech4.ui.onboarding.entername

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.adhi.challengech4.R
import com.adhi.challengech4.databinding.FragmentEnterNameBinding
import com.adhi.challengech4.ui.onboarding.OnFinishNavigateListener

class EnterNameFragment : Fragment(), OnFinishNavigateListener {

    private lateinit var binding: FragmentEnterNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onFinishNavigateListener() {
        val name = binding.etName.text.toString().trim()
        if(name.isEmpty()){
            Toast.makeText(requireContext(),"Please input your name:",Toast.LENGTH_SHORT).show()
        }else{
            navigateToMenu()
        }
    }


    private fun navigateToMenu() {

    }
}