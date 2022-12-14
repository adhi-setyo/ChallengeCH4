package com.adhi.challengech4.ui.menu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adhi.challengech4.R
import com.adhi.challengech4.databinding.ActivityMenuGameBinding
import com.adhi.challengech4.ui.game.GameActivity
import com.adhi.challengech4.ui.onboarding.entername.EnterNameFragment

class MenuGameActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMenuGameBinding

    private val name: String? by lazy {
        intent.getStringExtra(EXTRA_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setNameOnDesc()
        setMenuClickListener()
    }

    private fun setMenuClickListener(){
        binding.ivPlayerVsPlayer.setOnClickListener {
            GameActivity.startActivity(this,true, getString(R.string.placeholder_player_1,name))
        }
        binding.ivPlayerVsComp.setOnClickListener {
            GameActivity.startActivity(this,false, getString(R.string.placeholder_player_1,name))
        }
    }

    private fun setNameOnDesc(){
        binding.tvDescPlayer.text = getString(R.string.placeholder_desc_menu_player,name)
        binding.tvDescComp.text = getString(R.string.placeholder_desc_menu_comp,name)
    }

    companion object{
        private const val EXTRA_NAME = "EXTRA_NAME"

        fun startActivity(context: Context, name:String){
            context.startActivity(Intent(context,MenuGameActivity::class.java).apply {
                putExtra(EXTRA_NAME,name)
            })
        }
    }
}