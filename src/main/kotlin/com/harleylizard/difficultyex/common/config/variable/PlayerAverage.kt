package com.harleylizard.difficultyex.common.config.variable

import com.google.gson.JsonObject
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.minecraft.world.entity.LivingEntity

class PlayerAverage private constructor(private val range: Double) : Global {
    override val name = NAME

    override fun invoke(entity: LivingEntity): Double {
        var total = 1.0
        val players = PlayerLookup.tracking(entity)
        for (player in players) {

        }
        return total / players.size.coerceAtLeast(1)
    }

    override fun serialise(entry: JsonObject) {
        entry.addProperty("range", range)
    }

    companion object {
        const val NAME = "player_average"

        fun averageOf(range: Double) = PlayerAverage(range)

    }
}