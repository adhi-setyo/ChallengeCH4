package com.adhi.challengech4.ui.dialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.adhi.challengech4.R
import com.adhi.challengech4.databinding.FragmentResultDialogBinding

class ResultDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentResultDialogBinding
    private var listener : OnMenuSelectedListener? = null

    fun setOnMenuSelectedListener(listener: OnMenuSelectedListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val message = bundle?.getString("result")
        val messageView = binding.tvDescWin
        messageView.text = message

        binding.tvPlayAgain.setOnClickListener {
            listener?.onPlayAgain(this)
        }
        binding.tvBackMenu.setOnClickListener {
            listener?.onBacToMenu(this)
        }
    }
}

interface OnMenuSelectedListener {
    fun onPlayAgain(dialog : DialogFragment)
    fun onBacToMenu(dialog : DialogFragment)
}
