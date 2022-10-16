package com.adhi.challengech4.ui.game

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.adhi.challengech4.R
import com.adhi.challengech4.databinding.ActivityGameBinding
import com.adhi.challengech4.enum.*
import com.adhi.challengech4.manager.MultiplerSuitGameManager
import com.adhi.challengech4.manager.SuitGameManagerImpl
import com.adhi.challengech4.manager.SuitGameListener
import com.adhi.challengech4.manager.SuitGameManager
import com.adhi.challengech4.model.Player
import com.adhi.challengech4.ui.dialog.OnMenuSelectedListener
import com.adhi.challengech4.ui.dialog.ResultDialogFragment
import com.adhi.challengech4.ui.menu.MenuGameActivity

class GameActivity : AppCompatActivity(), SuitGameListener{

    private val binding: ActivityGameBinding by lazy{
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val isUsingMultiplayerMode: Boolean by lazy {
        intent.getBooleanExtra(EXTRAS_MULTIPLAYER_MODE,false)
    }

    private val name: String? by lazy {
        intent.getStringExtra(EXTRA_NAME)
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
        binding.tvPlayerSide1.text = getString(R.string.placeholder_player_1, name)
    }

    private fun setOnClickListener() {
       binding.ivArrowUp.setOnClickListener {
            suitGameManager.choosePlayerRock()
        }
        binding.ivArrowDown.setOnClickListener {
            suitGameManager.choosePlayerScissor()
        }
        binding.ivRefresh.setOnClickListener {
            suitGameManager.startOrResetGame()
        }
    }

    override fun onPlayerStatusChanged(player: Player, iconDrawable: Int) {
        setChooseCharacter(player,iconDrawable)
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


                when(player.playerCharacter) {
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
                    PlayerCharacter.SCISSOR -> {
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

        binding.ivLeftRock.setBackgroundColor(Color.TRANSPARENT)
        binding.ivLeftPaper.setBackgroundColor(Color.TRANSPARENT)
        binding.ivLeftScissor.setBackgroundColor(Color.TRANSPARENT)


        when (gameState) {
            GameState.IDLE -> {
                binding.ivVersus.isVisible = true
                setCharacterVisibility(isPlayerOneVisible = true, isPlayerTwoVisible = true)
            }
            GameState.STARTED -> {
                binding.ivVersus.isVisible = true
                setCharacterVisibility(isPlayerOneVisible = true, isPlayerTwoVisible = true)
            }
            GameState.FINISHED -> {
                binding.ivVersus.isVisible = true
                setCharacterVisibility(isPlayerOneVisible = true, isPlayerTwoVisible = true)
            }
            GameState.PLAYER_ONE_TURN -> {
                binding.ivVersus.isVisible = true
                setCharacterVisibility(isPlayerOneVisible = true, isPlayerTwoVisible = false)
            }
            GameState.PLAYER_TWO_TURN -> {
                binding.ivVersus.isVisible = true
                setCharacterVisibility(isPlayerOneVisible = false, isPlayerTwoVisible = true)
            }
        }
    }

    private fun setCharacterVisibility(isPlayerOneVisible: Boolean, isPlayerTwoVisible: Boolean) {
        binding.llPlayerleft.isVisible = isPlayerOneVisible
        binding.llPlayerright.isVisible = isPlayerTwoVisible
    }

    override fun onGameFinished(gameState: GameState, winner: Player) {
        val result:String

        when (winner.playerSide) {
            PlayerSide.PLAYER_ONE -> {
                result = "$name \n Menang"
                onSelectedMenu()
            }
            PlayerSide.PLAYER_TWO -> {
                result ="Player 2 \n Menang"
                onSelectedMenu()

            }
            PlayerSide.PLAYER_DRAW -> {
                result = "Player Draw"
                onSelectedMenu()
            }
        }
    }

    private fun onSelectedMenu(){
        ResultDialogFragment().apply {

            setOnMenuSelectedListener(object : OnMenuSelectedListener{
                override fun onPlayAgain(dialog: DialogFragment) {
                    dialog.dismiss()
                    suitGameManager.startOrResetGame()
                    Toast.makeText(context,"Play Again Please!!!", Toast.LENGTH_SHORT).show()
                }

                override fun onBacToMenu(dialog: DialogFragment) {
                    MenuGameActivity.startActivity(this@GameActivity, name.toString())
                    Toast.makeText(context,"Back To Menu!!!", Toast.LENGTH_SHORT).show()
                }
            })
        }.show(supportFragmentManager,null)
    }



    companion object {
        private const val EXTRAS_MULTIPLAYER_MODE = "EXTRAS_MULTIPLAYER_MODE"
        private const val EXTRA_NAME = "EXTRA_NAME"

        fun startActivity(context: Context, isUsingMultiplayerMode: Boolean,name: String) {
            context.startActivity(Intent(context, GameActivity::class.java).apply {
                putExtra(EXTRAS_MULTIPLAYER_MODE, isUsingMultiplayerMode)
                putExtra(EXTRA_NAME,name)
            })
        }

    }

}