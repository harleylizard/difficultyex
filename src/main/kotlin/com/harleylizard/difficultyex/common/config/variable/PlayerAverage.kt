package com.harleylizard.difficultyex.common.config.variable

import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.minecraft.world.entity.LivingEntity

class PlayerAverage private constructor() : Global {
    override val name = NAME

    override fun invoke(entity: LivingEntity): Double {
        var total = 1.0
        val players = PlayerLookup.tracking(entity)
        for (player in players) {

        }
        return total / players.size
    }

    companion object {
        const val NAME = "player_average"

        val instance = PlayerAverage()

    }
}