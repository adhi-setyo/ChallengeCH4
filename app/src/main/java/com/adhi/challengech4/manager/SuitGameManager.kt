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
    fun onGameChanged(gameState: GameState)
    fun onGameFinished(gameState: GameState, winner: Player)

}

open class SuitGameManagerImpl(
    private val listener: SuitGameListener
):SuitGameManager{

    protected lateinit var playerOne : Player
    protected lateinit var playerTwo : Player
    protected lateinit var playerDraw : Player
    protected lateinit var state : GameState

    override fun initGame() {
        setGameState(GameState.IDLE)
        playerOne = Player(PlayerSide.PLAYER_ONE, PlayerState.IDLE, PlayerPosition.MIDDLE, PlayerCharacter.PAPER)
        playerTwo = Player(PlayerSide.PLAYER_TWO, PlayerState.IDLE, PlayerPosition.MIDDLE, PlayerCharacter.PAPER)
        playerDraw = Player(PlayerSide.PLAYER_DRAW, PlayerState.IDLE, PlayerPosition.MIDDLE, PlayerCharacter.PAPER)
        notifyPlayerDataChanged()
        setGameState(GameState.STARTED)
    }

    private fun notifyPlayerDataChanged() {
        listener.onPlayerStatusChanged(playerOne, getPlayerOneDrawableByState(playerOne.playerCharacter, playerOne.playerState))
        listener.onPlayerStatusChanged(playerTwo, getPlayerTwoDrawableByState(playerTwo.playerCharacter, playerTwo.playerState))
    }

    override fun choosePlayerRock() {
        if(state != GameState.FINISHED && playerOne.playerCharacter.ordinal  > PlayerCharacter.ROCK.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerCharacterByOrdinal(currentIndex -1))
            startGame()
        }
    }

    override fun choosePlayerPaper() {
        if(state != GameState.FINISHED && playerOne.playerCharacter.ordinal == PlayerCharacter.PAPER.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerCharacterByOrdinal(currentIndex))
            startGame()
        }
    }

    override fun choosePlayerScissor() {
        if(state != GameState.FINISHED && playerOne.playerCharacter.ordinal < PlayerCharacter.SCISSOR.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerCharacterByOrdinal(currentIndex +1))
            startGame()
        }
    }

    protected fun setPlayerOneCharacter(
        playerCharacter: PlayerCharacter = playerOne.playerCharacter,
    ){
        playerOne.apply {
            this.playerCharacter = playerCharacter
        }
        listener.onPlayerStatusChanged(playerOne,
            getPlayerOneDrawableByState(playerOne.playerCharacter, playerOne.playerState))
    }

    protected fun setPlayerTwoCharacter(
        playerCharacter: PlayerCharacter = playerTwo.playerCharacter,
    ){
        playerOne.apply {
            this.playerCharacter = playerCharacter
        }
        listener.onPlayerStatusChanged(playerTwo,
            getPlayerTwoDrawableByState(playerTwo.playerCharacter, playerTwo.playerState))
    }

    private fun getPlayerOneDrawableByState(playerCharacter: PlayerCharacter, playerState: PlayerState): Int {
        return when(playerState) {
            PlayerState.IDLE -> when (playerCharacter) {
                PlayerCharacter.ROCK -> R.drawable.batu
                PlayerCharacter.PAPER -> R.drawable.kertas
                PlayerCharacter.SCISSOR -> R.drawable.gunting
            }
            PlayerState.STARTED -> when (playerCharacter) {
                PlayerCharacter.ROCK -> R.drawable.batu
                PlayerCharacter.PAPER -> R.drawable.kertas
                PlayerCharacter.SCISSOR -> R.drawable.gunting
            }
            PlayerState.FINISHED -> when (playerCharacter) {
                PlayerCharacter.ROCK -> R.drawable.batu
                PlayerCharacter.PAPER -> R.drawable.kertas
                PlayerCharacter.SCISSOR -> R.drawable.gunting
            }
        }
    }

    private fun getPlayerTwoDrawableByState (playerCharacter: PlayerCharacter, playerState: PlayerState): Int {
        return when(playerState) {
            PlayerState.IDLE -> when (playerCharacter) {
                PlayerCharacter.ROCK -> R.drawable.batu
                PlayerCharacter.PAPER -> R.drawable.kertas
                PlayerCharacter.SCISSOR -> R.drawable.gunting
            }
            PlayerState.STARTED -> when (playerCharacter) {
                PlayerCharacter.ROCK -> R.drawable.batu
                PlayerCharacter.PAPER -> R.drawable.kertas
                PlayerCharacter.SCISSOR -> R.drawable.gunting
            }
            PlayerState.FINISHED -> when (playerCharacter) {
                PlayerCharacter.ROCK -> R.drawable.batu
                PlayerCharacter.PAPER -> R.drawable.kertas
                PlayerCharacter.SCISSOR -> R.drawable.gunting
            }
        }
    }

    protected fun getPlayerCharacterByOrdinal(index: Int): PlayerCharacter {
        return PlayerCharacter.values()[index]
    }

    protected fun setGameState(newGameState: GameState) {
        state = newGameState
        listener.onGameChanged(state)
    }

    protected fun startGame() {
        playerTwo.apply {
            playerCharacter = getPlayerTwoPostion()
        }
        checkPlayerWinner()
    }

    open fun getPlayerTwoPostion(): PlayerCharacter {
        val randomCharacter = Random.nextInt(0, until = PlayerCharacter.values().size)
        return getPlayerCharacterByOrdinal(randomCharacter)

    }

    private fun checkPlayerWinner() {
       val winner =

           // Player One Win
           if(playerOne.playerCharacter.ordinal == 0 && playerTwo.playerCharacter.ordinal == 2){
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
       }

           // Player Two Win
           else if(playerOne.playerCharacter.ordinal == 2 && playerTwo.playerCharacter.ordinal == 0){
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
       }

           // Player Draw
           else if(playerOne.playerCharacter.ordinal == 0 && playerTwo.playerCharacter.ordinal == 0) {
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.ROCK)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.ROCK)
           playerDraw
       }else if(playerOne.playerCharacter.ordinal == 1 && playerTwo.playerCharacter.ordinal == 1) {
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.PAPER)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.PAPER)
           playerDraw
       }else if(playerOne.playerCharacter.ordinal == 2 && playerTwo.playerCharacter.ordinal == 2) {
           setPlayerOneCharacter(playerCharacter = PlayerCharacter.SCISSOR)
           setPlayerTwoCharacter(playerCharacter = PlayerCharacter.SCISSOR)
           playerDraw
       }else{
           return
       }
        setGameState(GameState.FINISHED)
        listener.onGameFinished(state,winner)
    }

    protected fun reset(){
        initGame()
    }

    override fun startOrResetGame() {
        if(state != GameState.FINISHED){
            startGame()
        }else{
            reset()
        }
    }
}

class MultiplerSuitGameManager(listener: SuitGameListener):SuitGameManagerImpl(listener){

    override fun initGame() {
        super.initGame()
        setGameState(GameState.PLAYER_ONE_TURN)
    }

    override fun getPlayerTwoPostion(): PlayerCharacter {
        return playerTwo.playerCharacter
    }

    override fun choosePlayerRock() {
        if(state == GameState.PLAYER_ONE_TURN){
            super.choosePlayerRock()
        }else if(state == GameState.PLAYER_TWO_TURN){
            if(playerTwo.playerCharacter.ordinal  > PlayerCharacter.ROCK.ordinal){
                val currentIndex = playerTwo.playerCharacter.ordinal
                setPlayerTwoCharacter(getPlayerCharacterByOrdinal(currentIndex -1))
                startGame()
            }
        }
    }

    override fun choosePlayerPaper() {
        if(state == GameState.PLAYER_ONE_TURN){
            super.choosePlayerPaper()
        }else if(state == GameState.PLAYER_TWO_TURN){
            if(playerTwo.playerCharacter.ordinal < PlayerCharacter.SCISSOR.ordinal){
                val currentIndex = playerTwo.playerCharacter.ordinal
                setPlayerTwoCharacter(getPlayerCharacterByOrdinal(currentIndex +1))
                startGame()
            }
        }
    }

    override fun choosePlayerScissor() {
        if(state == GameState.PLAYER_ONE_TURN){
            super.choosePlayerScissor()
        }else if(state == GameState.PLAYER_TWO_TURN){
            if(playerTwo.playerCharacter.ordinal < PlayerCharacter.SCISSOR.ordinal){
                val currentIndex = playerTwo.playerCharacter.ordinal
                setPlayerTwoCharacter(getPlayerCharacterByOrdinal(currentIndex +1))
                startGame()
            }
        }
    }

    override fun startOrResetGame() {
        when(state){
            GameState.PLAYER_ONE_TURN->{
                setGameState(GameState.PLAYER_TWO_TURN)
            }
            GameState.PLAYER_TWO_TURN -> {
                startGame()
            }
            GameState.FINISHED -> {
                reset()
            }
            else -> return
        }
    }
}