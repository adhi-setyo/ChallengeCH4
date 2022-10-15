package com.adhi.challengech4.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.adhi.challengech4.R
import com.adhi.challengech4.databinding.FragmentResultDialogBinding
import com.adhi.challengech4.enum.PlayerSide
import com.adhi.challengech4.model.Player

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
        binding.tvPlayAgain.setOnClickListener{
            listener?.onPlayAgain(this)
        }
        binding.tvBackMenu.setOnClickListener{
            listener?.onBacToMenu(this)
        }
    }
}

interface OnMenuSelectedListener {
    fun onPlayAgain(dialog : DialogFragment)
    fun onBacToMenu(dialog : DialogFragment)

}
