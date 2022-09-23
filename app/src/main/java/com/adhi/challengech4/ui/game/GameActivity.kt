package com.adhi.challengech4.ui.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.core.content.ContextCompat
import com.adhi.challengech4.R
import com.adhi.challengech4.databinding.ActivityGameBinding
import com.adhi.challengech4.enum.GameState
import com.adhi.challengech4.enum.PlayerCharacter
import com.adhi.challengech4.enum.PlayerPosition
import com.adhi.challengech4.enum.PlayerSide
import com.adhi.challengech4.manager.ComputerEnemySuitGameManager
import com.adhi.challengech4.manager.SuitGameListener
import com.adhi.challengech4.manager.SuitGameManager
import com.adhi.challengech4.model.Player

class GameActivity : AppCompatActivity(), SuitGameListener {

    private val binding: ActivityGameBinding by lazy{
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val suitGameManager: SuitGameManager by lazy{
        ComputerEnemySuitGameManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        suitGameManager.initGame()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.ivLeftRock.setOnClickListener{
            suitGameManager.choosePlayerRock()
             Log.d("Button", "Rock Character")
        }
        binding.ivLeftPaper.setOnClickListener{
            suitGameManager.choosePlayerPaper()
            Log.d("Button", "Paper Character")

        }
        binding.ivLeftScissor.setOnClickListener{
            suitGameManager.choosePlayerScissor()
            Log.d("Button", "Scissor Character")

        }
        binding.ivReset.setOnClickListener{
            suitGameManager.ResetGame()
            Log.d("Button", "For Reset Game")

        }
    }

    override fun onPlayerStatusChanged(player: Player, iconDrawable: Int) {
        setChooseCharacter(player, iconDrawable)
    }

    private fun setChooseCharacter(player: Player, iconDrawable: Int) {
        val ivRock: ImageView?
        val ivPaper: ImageView?
        val ivScissor: ImageView?
        val drawable = ContextCompat.getDrawable(this, iconDrawable)

        if (player.playerSide == PlayerSide.PLAYER_ONE) {
            ivRock = binding.ivLeftRock
            ivPaper = binding.ivLeftPaper
            ivScissor = binding.ivLeftScissor
        } else {
            ivRock = binding.ivRightRock
            ivPaper = binding.ivRightPaper
            ivScissor = binding.ivRightScissor
        }

        when (player.playerCharacter) {
            PlayerCharacter.ROCK -> {
                ivRock.visibility = View.VISIBLE
                ivPaper.visibility = View.VISIBLE
                ivScissor.visibility = View.VISIBLE
                ivRock.setImageDrawable(drawable)
            }
            PlayerCharacter.PAPER -> {
                ivRock.visibility = View.VISIBLE
                ivPaper.visibility = View.VISIBLE
                ivScissor.visibility = View.VISIBLE
                ivPaper.setImageDrawable(drawable)
            }
            PlayerCharacter.SCISSOR-> {
                ivRock.visibility = View.VISIBLE
                ivPaper.visibility = View.VISIBLE
                ivScissor.visibility = View.VISIBLE
                ivScissor.setImageDrawable(drawable)
            }
        }
    }

    override fun onGameChanged(gameState: GameState) {

    }
    
    override fun onGameFinished(gameState: GameState, winner: Player) {
       if(winner.playerSide == PlayerSide.PLAYER_ONE){
           binding.ivVersus.visibility = View.INVISIBLE
           binding.ivPlayerone.visibility = View.VISIBLE
       }else if(winner.playerSide == PlayerSide.PLAYER_TWO){
           binding.ivVersus.visibility = View.INVISIBLE
           binding.ivPlayertwo.visibility = View.VISIBLE
       }else{
           binding.ivVersus.visibility = View.INVISIBLE
           binding.ivDraw.visibility = View.VISIBLE
       }
    }


}