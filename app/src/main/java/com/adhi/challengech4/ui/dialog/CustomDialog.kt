package com.adhi.challengech4.ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.adhi.challengech4.R
import com.adhi.challengech4.databinding.FragmentCustomDialogBinding
import com.adhi.challengech4.model.GetName

class CustomDialog : DialogFragment() {

    private lateinit var binding: FragmentCustomDialogBinding
    private var listener : OnMenuSelectedListener2? =null
    private var result: GetName? = null

    fun setOnMenuSelectedListener2(listener: OnMenuSelectedListener2) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            result = it.getParcelable(ARG_GET_RESULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDescWin.text = getString(R.string.placeholder_winner,result)

        binding.tvPlayAgain.setOnClickListener {
            listener?.onPlayAgain(this)
        }
        binding.tvBackMenu.setOnClickListener {
            listener?.onBacToMenu(this)
        }
    }

    companion object {
        private const val ARG_GET_RESULT = "ARG_GET_RESULT"

        @JvmStatic
        fun newInstance(result: GetName) =
            CustomDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GET_RESULT, result)
                }
            }
    }
}

interface OnMenuSelectedListener2 {
    fun onPlayAgain(dialog : DialogFragment)
    fun onBacToMenu(dialog : DialogFragment)
}