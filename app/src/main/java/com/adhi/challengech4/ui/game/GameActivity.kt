package com.adhi.challengech4.ui.game

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.adhi.challengech4.databinding.ActivityGameBinding
import com.adhi.challengech4.enum.*
import com.adhi.challengech4.manager.MultiplerSuitGameManager
import com.adhi.challengech4.manager.SuitGameManagerImpl
import com.adhi.challengech4.manager.SuitGameListener
import com.adhi.challengech4.manager.SuitGameManager
import com.adhi.challengech4.model.Player

class GameActivity : AppCompatActivity(), SuitGameListener{

    private val binding: ActivityGameBinding by lazy{
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val isUsingMultiplayerMode: Boolean by lazy {
        intent.getBooleanExtra(EXTRAS_MULTIPLAYER_MODE,false)
    }

    private val suitGameManager: SuitGameManager by lazy{
        if(isUsingMultiplayerMode){
            MultiplerSuitGameManager(this)
        }else{
            SuitGameManagerImpl(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        suitGameManager.initGame()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.ivLeftRock.setOnClickListener{
            suitGameManager.choosePlayerRock()
            Log.d("Button", "Rock Character")
            binding.ivLeftRock.setBackgroundColor(Color.GRAY)
        }
        binding.ivLeftPaper.setOnClickListener{
            suitGameManager.choosePlayerPaper()
            Log.d("Button", "Paper Character")
            binding.ivLeftPaper.setBackgroundColor(Color.GRAY)
        }
        binding.ivLeftScissor.setOnClickListener{
            suitGameManager.choosePlayerScissor()
            Log.d("Button", "Scissor Character")
            binding.ivLeftScissor.setBackgroundColor(Color.GRAY)
        }
        binding.ivRefresh.setOnClickListener{
            suitGameManager.startOrResetGame()
            Log.d("Button", "For Reset Game")
            binding.ivRightRock.setBackgroundColor(Color.TRANSPARENT)
            binding.ivRightPaper.setBackgroundColor(Color.TRANSPARENT)
            binding.ivRightScissor.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onPlayerStatusChanged(player: Player, iconDrawable: Int) {
        setChooseCharacter(player)
    }

    private fun setChooseCharacter(player: Player) {
        val ivRock: ImageView?
        val ivPaper: ImageView?
        val ivScissor: ImageView?

        if (player.playerSide == PlayerSide.PLAYER_ONE) {
            ivRock = binding.ivLeftRock
            ivPaper = binding.ivLeftPaper
            ivScissor = binding.ivLeftScissor
        } else {
            ivRock = binding.ivRightRock
            ivPaper = binding.ivRightPaper
            ivScissor = binding.ivRightScissor
        }

         when(player.playerCharacter) {
             PlayerCharacter.ROCK -> {
                 ivRock.visibility = View.VISIBLE
                 ivPaper.visibility = View.VISIBLE
                 ivScissor.visibility = View.VISIBLE
                 ivRock.setBackgroundColor(Color.GRAY)
             }
             PlayerCharacter.PAPER -> {
                 ivRock.visibility = View.VISIBLE
                 ivPaper.visibility = View.VISIBLE
                 ivScissor.visibility = View.VISIBLE
                 ivPaper.setBackgroundColor(Color.GRAY)
             }
             PlayerCharacter.SCISSOR -> {
                 ivRock.visibility = View.VISIBLE
                 ivPaper.visibility = View.VISIBLE
                 ivScissor.visibility = View.VISIBLE
                 ivScissor.setBackgroundColor(Color.GRAY)
             }
         }
    }

    override fun onGameChanged(gameState: GameState) {
        binding.ivPlayerone.visibility=View.INVISIBLE
        binding.ivPlayertwo.visibility=View.INVISIBLE
        binding.ivDraw.visibility=View.INVISIBLE

        //left
        binding.ivLeftRock.setBackgroundColor(Color.TRANSPARENT)
        binding.ivLeftPaper.setBackgroundColor(Color.TRANSPARENT)
        binding.ivLeftScissor.setBackgroundColor(Color.TRANSPARENT)

    }

    override fun onGameFinished(gameState: GameState, winner: Player) {
        when (winner.playerSide) {
            PlayerSide.PLAYER_ONE -> {
                binding.ivPlayerone.visibility = View.VISIBLE
            }
            PlayerSide.PLAYER_TWO -> {
                binding.ivPlayertwo.visibility = View.VISIBLE
            }
            PlayerSide.PLAYER_DRAW -> {
                binding.ivDraw.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private const val EXTRAS_MULTIPLAYER_MODE = "EXTRAS_MULTIPLAYER_MODE"

        fun startActivity(context: Context, isUsingMultiplayerMode: Boolean) {
            context.startActivity(Intent(context, GameActivity::class.java).apply {
                putExtra(EXTRAS_MULTIPLAYER_MODE, isUsingMultiplayerMode)
            })
        }
    }

}