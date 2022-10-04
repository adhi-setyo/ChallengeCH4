package com.adhi.challengech4.manager

import com.adhi.challengech4.R
import com.adhi.challengech4.enum.*
import com.adhi.challengech4.model.Player
import kotlin.random.Random

interface SuitGameManager {
    fun initGame()
    fun startOrResetGame()
    fun choosePlayerRock()
    fun choosePlayerPaper()
    fun choosePlayerScissor()

}

interface SuitGameListener{
    fun onPlayerStatusChanged(player: Player, iconDrawable: Int)
    //fun onPlayerStatusChanged2(player: Player, colorDrawable: Int)
    fun onGameChanged(gameState: GameState)
    fun onGameFinished(gameState: GameState, winner: Player)

}

class ComputerEnemySuitGameManager(
    private val listener: SuitGameListener
):SuitGameManager{

    private lateinit var playerOne : Player
    private lateinit var playerTwo : Player
    private lateinit var playerThree : Player
    private lateinit var gameState : GameState

    override fun initGame() {
        setGameState(GameState.IDLE)
        playerOne = Player(PlayerSide.PLAYER_ONE, PlayerState.IDLE, PlayerPosition.MIDDLE, PlayerCharacter.PAPER)
        playerTwo = Player(PlayerSide.PLAYER_TWO, PlayerState.IDLE, PlayerPosition.MIDDLE, PlayerCharacter.PAPER)
        playerThree = Player(PlayerSide.PLAYER_THREE, PlayerState.IDLE, PlayerPosition.MIDDLE, PlayerCharacter.PAPER)
        notifyPlayerDataChanged()
        setGameState(GameState.STARTED)
    }

    private fun notifyPlayerDataChanged() {
        listener.onPlayerStatusChanged(playerOne, getPlayerOneDrawableByState(playerOne.playerCharacter))
        listener.onPlayerStatusChanged(playerTwo, getPlayerTwoDrawableByState(playerTwo.playerCharacter))
    }

    override fun choosePlayerRock() {
        if(gameState != GameState.FINISHED && playerOne.playerCharacter.ordinal  > PlayerCharacter.ROCK.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerCharacterByOrdinal(currentIndex -1))
            startGame()
        }
    }

    override fun choosePlayerPaper() {
        if(gameState != GameState.FINISHED && playerOne.playerCharacter.ordinal == PlayerCharacter.PAPER.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerCharacterByOrdinal(currentIndex))
            startGame()
        }
    }

    override fun choosePlayerScissor() {
        if(gameState != GameState.FINISHED && playerOne.playerCharacter.ordinal < PlayerCharacter.SCISSOR.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerCharacterByOrdinal(currentIndex +1))
            startGame()
        }
    }

    private fun setPlayerOneCharacter(
        playerCharacter: PlayerCharacter = playerOne.playerCharacter,
        //playerState: PlayerState = playerOne.playerState,
        //playerPosition: PlayerPosition = playerOne.playerPosition
    ){
        playerOne.apply {
            this.playerCharacter = playerCharacter
            //this.playerState = playerState
            //this.playerPosition = playerPosition
        }
        listener.onPlayerStatusChanged(playerOne,
            getPlayerOneDrawableByState(playerOne.playerCharacter))
    }

    private fun setPlayerTwoCharacter(
        playerCharacter: PlayerCharacter = playerTwo.playerCharacter,
        //playerState: PlayerState = playerTwo.playerState,
        //playerPosition: PlayerPosition = playerTwo.playerPosition
    ){
        playerOne.apply {
            this.playerCharacter = playerCharacter
            //this.playerState = playerState
            //this.playerPosition = playerPosition
        }
        listener.onPlayerStatusChanged(playerTwo,
            getPlayerTwoDrawableByState(playerTwo.playerCharacter))
    }

    private fun getPlayerOneDrawableByState(playerCharacter: PlayerCharacter): Int {
        return when (playerCharacter) {
            PlayerCharacter.ROCK -> R.drawable.batu
            PlayerCharacter.PAPER -> R.drawable.kertas
            PlayerCharacter.SCISSOR -> R.drawable.gunting
        }
    }

    private fun getPlayerTwoDrawableByState (playerCharacter: PlayerCharacter): Int {
        return when (playerCharacter) {
            PlayerCharacter.ROCK -> R.drawable.batu
            PlayerCharacter.PAPER -> R.drawable.kertas
            PlayerCharacter.SCISSOR -> R.drawable.gunting
        }
    }

    private fun getPlayerCharacterByOrdinal(index: Int): PlayerCharacter {
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
        val randomCharacter = Random.nextInt(0, until = PlayerCharacter.values().size)
        return getPlayerCharacterByOrdinal(randomCharacter)
    }

    private fun checkPlayerWinner() {
       val winner = if(playerOne.playerCharacter.ordinal == 0 && playerTwo.playerCharacter.ordinal == 2){
            setPlayerOneCharacter(playerCharacter = PlayerCharacter.ROCK)
            setPlayerTwoCharacter(playerCharacter = PlayerCharacter.SCISSOR)
            playerOne
        }else if(playerOne.playerCharacter.ordinal == 1 && playerTwo.playerCharacter.ordinal == 0){
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.PAPER)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.ROCK)
           playerOne
       }else if(playerOne.playerCharacter.ordinal == 2 && playerTwo.playerCharacter.ordinal == 1){
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.SCISSOR)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.PAPER)
           playerOne
       }else if(playerOne.playerCharacter.ordinal == 2 && playerTwo.playerCharacter.ordinal == 0){
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.SCISSOR)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.ROCK)
           playerTwo
        }else if(playerOne.playerCharacter.ordinal == 1 && playerTwo.playerCharacter.ordinal == 2){
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.PAPER)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.SCISSOR)
           playerTwo
       }else if(playerOne.playerCharacter.ordinal == 0 && playerTwo.playerCharacter.ordinal == 1){
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.ROCK)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.PAPER)
           playerTwo
       }else if(playerOne.playerCharacter.ordinal == 0 && playerTwo.playerCharacter.ordinal == 0) {
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.ROCK)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.ROCK)
           playerThree
       }else if(playerOne.playerCharacter.ordinal == 1 && playerTwo.playerCharacter.ordinal == 1) {
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.PAPER)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.PAPER)
           playerThree
       }else if(playerOne.playerCharacter.ordinal == 2 && playerTwo.playerCharacter.ordinal == 2) {
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.SCISSOR)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.SCISSOR)
           playerThree
       }else{
           return
       }
        setGameState(GameState.FINISHED)
        listener.onGameFinished(gameState,winner)
    }

    private fun reset(){
        initGame()
    }

    override fun startOrResetGame() {
        reset()
    }

}