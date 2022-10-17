package com.adhi.challengech4.ui.game

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.adhi.challengech4.model.GetName
import com.adhi.challengech4.model.Player
import com.adhi.challengech4.ui.dialog.CustomDialog
import com.adhi.challengech4.ui.dialog.OnMenuSelectedListener2
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
       checkGameWinner(winner)
    }


    var value:String? = null
    private fun checkGameWinner(winner: Player){

        //val resultFragment = ResultDialogFragment()

        when (winner.playerSide) {
            PlayerSide.PLAYER_ONE -> {
                value = "$name \n Menang"
                Toast.makeText(this,value,Toast.LENGTH_SHORT).show()
                onSelectedMenuDialog()
            }
            PlayerSide.PLAYER_TWO -> {
                value ="Player 2 \n Menang"
                Toast.makeText(this,value,Toast.LENGTH_SHORT).show()
                onSelectedMenuDialog()

            }
            PlayerSide.PLAYER_DRAW -> {
                value = "Player Draw"
                Toast.makeText(this,value,Toast.LENGTH_SHORT).show()
                onSelectedMenuDialog()
            }
        }
       /* CustomDialog.newInstance(
            GetName(
                desc = value
            )
        )*/
    }

    private fun onSelectedMenuDialog(){
        /*CustomDialog().apply {
            setOnMenuSelectedListener2(object : OnMenuSelectedListener2{
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
        }.show(supportFragmentManager,null)*/


       /* ResultDialogFragment().apply {
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
        }.show(supportFragmentManager,null)*/

        AlertDialog.Builder(this@GameActivity)
            .setTitle(getString(R.string.text_title_alert))
            .setMessage(getString(R.string.placeholder_winner,value))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.text_play_again)) {dialog, id ->
                suitGameManager.startOrResetGame()
            }
            .setNegativeButton(getString(R.string.text_back_menu)){dialog, id ->
                MenuGameActivity.startActivity(this@GameActivity, name.toString())
            }.create().show()
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