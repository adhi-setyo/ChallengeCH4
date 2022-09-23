package com.adhi.challengech4.model

import com.adhi.challengech4.enum.PlayerCharacter
import com.adhi.challengech4.enum.PlayerPosition
import com.adhi.challengech4.enum.PlayerSide
import com.adhi.challengech4.enum.PlayerState

data class Player(
    val playerSide: PlayerSide,
    var playerState: PlayerState,
    var playerPosition: PlayerPosition,
    var playerCharacter : PlayerCharacter
)
