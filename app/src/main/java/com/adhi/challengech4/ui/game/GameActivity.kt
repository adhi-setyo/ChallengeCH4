package com.adhi.challengech4.ui.game

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
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
            binding.ivLeftRock.visibility=View.VISIBLE
            binding.ivLeftPaper.visibility=View.INVISIBLE
            binding.ivLeftScissor.visibility=View.INVISIBLE
        }
        binding.ivLeftPaper.setOnClickListener{
            suitGameManager.choosePlayerPaper()
            Log.d("Button", "Paper Character")
            binding.ivLeftPaper.visibility=View.VISIBLE
            binding.ivLeftRock.visibility=View.INVISIBLE
            binding.ivLeftScissor.visibility=View.INVISIBLE
        }
        binding.ivLeftScissor.setOnClickListener{
            suitGameManager.choosePlayerScissor()
            Log.d("Button", "Scissor Character")
            binding.ivLeftScissor.visibility=View.VISIBLE
            binding.ivLeftPaper.visibility=View.INVISIBLE
            binding.ivLeftRock.visibility=View.INVISIBLE
        }
        binding.ivRefresh.setOnClickListener{
            suitGameManager.startOrResetGame()
            Log.d("Button", "For Reset Game")
            binding.ivRightScissor.visibility=View.VISIBLE
            binding.ivRightPaper.visibility=View.VISIBLE
            binding.ivRightRock.visibility=View.VISIBLE
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
                ivPaper.visibility = View.INVISIBLE
                ivScissor.visibility = View.INVISIBLE
                ivRock.setImageDrawable(drawable)
            }
            PlayerCharacter.PAPER -> {
                ivRock.visibility = View.INVISIBLE
                ivPaper.visibility = View.VISIBLE
                ivScissor.visibility = View.INVISIBLE
                ivPaper.setImageDrawable(drawable)
            }
            PlayerCharacter.SCISSOR-> {
                ivRock.visibility = View.INVISIBLE
                ivPaper.visibility = View.INVISIBLE
                ivScissor.visibility = View.VISIBLE
                ivScissor.setImageDrawable(drawable)
            }
        }
    }

    override fun onGameChanged(gameState: GameState) {
        binding.ivPlayerone.visibility=View.INVISIBLE
        binding.ivPlayertwo.visibility=View.INVISIBLE
        binding.ivDraw.visibility=View.INVISIBLE

        //left
        binding.ivLeftRock.visibility=View.VISIBLE
        binding.ivLeftPaper.visibility=View.VISIBLE
        binding.ivLeftScissor.visibility=View.VISIBLE

        //right
        /*
        binding.ivRightRock.visibility=View.VISIBLE
        binding.ivRightPaper.visibility=View.VISIBLE
        binding.ivRightScissor.visibility=View.VISIBLE
        */


    }

    override fun onGameFinished(gameState: GameState, winner: Player) {
        if(winner.playerSide == PlayerSide.PLAYER_ONE){
            binding.ivPlayerone.visibility = View.VISIBLE
        }else if (winner.playerSide == PlayerSide.PLAYER_TWO){
            binding.ivPlayertwo.visibility = View.VISIBLE
        }else if (winner.playerSide == PlayerSide.PLAYER_THREE){
            binding.ivDraw.visibility = View.VISIBLE
        }
    }

}