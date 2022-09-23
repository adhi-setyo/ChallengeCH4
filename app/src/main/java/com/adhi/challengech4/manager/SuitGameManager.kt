package com.adhi.challengech4.manager

import android.graphics.drawable.AdaptiveIconDrawable
import com.adhi.challengech4.R
import com.adhi.challengech4.enum.*
import com.adhi.challengech4.model.Player
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

interface SuitGameManager {
    fun initGame()
    fun ResetGame()
    fun choosePlayerRock()
    fun choosePlayerPaper()
    fun choosePlayerScissor()

}

interface SuitGameListener{
    fun onPlayerStatusChanged(player: Player, iconDrawable: Int)
    fun onGameChanged(gameState: GameState)
    fun onGameFinished(gameState: GameState, winner: Player)

}

class ComputerEnemySuitGameManager(
    private val listener: SuitGameListener
):SuitGameManager{

    private lateinit var playerOne : Player
    private lateinit var playerTwo : Player
    private lateinit var gameState : GameState

    override fun initGame() {
        setGameState(GameState.IDLE)
        playerOne = Player(PlayerSide.PLAYER_ONE, PlayerState.IDLE, PlayerPosition.TOP,PlayerCharacter.ROCK)
        playerTwo = Player(PlayerSide.PLAYER_TWO, PlayerState.IDLE, PlayerPosition.TOP,PlayerCharacter.ROCK)
        notifyPlayerDataChanged()
        setGameState(GameState.STARTED)
    }

    private fun notifyPlayerDataChanged() {
        listener.onPlayerStatusChanged(playerOne, getPlayerOneDrawableByState(playerOne.playerCharacter, playerOne.playerState))
        listener.onPlayerStatusChanged(playerTwo, getPlayerTwoDrawableByState(playerTwo.playerCharacter, playerTwo.playerState))
    }

    override fun choosePlayerRock() {
        if(gameState != GameState.FINISHED && playerOne.playerCharacter.ordinal  > PlayerCharacter.ROCK.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerPostionByOrdinal(currentIndex -1), PlayerState.IDLE)
        }
    }

    override fun choosePlayerPaper() {
        if(gameState != GameState.FINISHED && playerOne.playerCharacter.ordinal == PlayerCharacter.PAPER.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerPostionByOrdinal(currentIndex), PlayerState.IDLE)
        }
    }

       override fun choosePlayerScissor() {
        if(gameState != GameState.FINISHED && playerOne.playerCharacter.ordinal < PlayerCharacter.SCISSOR.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerPostionByOrdinal(currentIndex +1), PlayerState.IDLE)
        }
    }

    private fun setPlayerOneCharacter(
        playerCharacter: PlayerCharacter = playerOne.playerCharacter,
        playerState: PlayerState = playerOne.playerState
    ){
        playerOne.apply {
            this.playerCharacter = playerCharacter
            this.playerState = playerState
        }
        listener.onPlayerStatusChanged(playerOne,
            getPlayerOneDrawableByState(playerOne.playerCharacter, playerOne.playerState))
    }

    private fun setPlayerTwoCharacter(
        playerCharacter: PlayerCharacter = playerTwo.playerCharacter,
        playerState: PlayerState = playerTwo.playerState
    ){
        playerOne.apply {
            this.playerCharacter = playerCharacter
            this.playerState = playerState
        }
        listener.onPlayerStatusChanged(playerTwo,
            getPlayerTwoDrawableByState(playerTwo.playerCharacter, playerTwo.playerState))
    }

    private fun getPlayerOneDrawableByState(playerCharacter: PlayerCharacter, playerState: PlayerState): Int {
        return when(playerState){
            PlayerState.IDLE -> when(playerCharacter){
                PlayerCharacter.ROCK -> R.drawable.batu
                PlayerCharacter.PAPER -> R.drawable.kertas
                PlayerCharacter.SCISSOR -> R.drawable.gunting
            }
            PlayerState.STARTED -> when(playerCharacter){
                PlayerCharacter.ROCK -> R.drawable.batu
                PlayerCharacter.PAPER -> R.drawable.kertas
                PlayerCharacter.SCISSOR -> R.drawable.gunting
            }
            PlayerState.FINISHED -> R.drawable.refresh
        }
    }

    private fun getPlayerTwoDrawableByState(playerCharacter: PlayerCharacter, playerState: PlayerState): Int {
        return when(playerState){
            PlayerState.IDLE -> when(playerCharacter){
                PlayerCharacter.ROCK -> R.drawable.batu
                PlayerCharacter.PAPER -> R.drawable.kertas
                PlayerCharacter.SCISSOR -> R.drawable.gunting
            }
            PlayerState.STARTED -> when(playerCharacter){
                PlayerCharacter.ROCK -> R.drawable.batu
                PlayerCharacter.PAPER -> R.drawable.kertas
                PlayerCharacter.SCISSOR -> R.drawable.gunting
            }
            PlayerState.FINISHED -> R.drawable.refresh
        }
    }

    private fun getPlayerPostionByOrdinal(index: Int): PlayerCharacter {
        return PlayerCharacter.values()[index]
    }

    private fun setGameState(newGameState: GameState) {
        gameState = newGameState
        listener.onGameChanged(gameState)
    }

    private fun startGame() {
        playerTwo.apply {
            playerCharacter = getPlayerTwoPostion()
        }
        checkPlayerWinner()
    }


    private fun getPlayerTwoPostion(): PlayerCharacter {
        val randomPosition = Random.nextInt(0, until = PlayerCharacter.values().size)
        return getPlayerPostionByOrdinal(randomPosition)
    }

    private fun checkPlayerWinner() {
        val winner = if(playerOne.playerCharacter == playerTwo.playerCharacter){
            setPlayerOneCharacter()
            setPlayerTwoCharacter()
            playerOne
        }else{
            setPlayerOneCharacter()
            setPlayerTwoCharacter()
            playerTwo
        }
        setGameState(GameState.FINISHED)
        listener.onGameFinished(gameState,winner)
    }

    private fun reset(){
        initGame()
    }

    override fun ResetGame() {
        reset()
    }
}