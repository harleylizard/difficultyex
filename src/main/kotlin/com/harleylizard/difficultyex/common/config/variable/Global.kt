package com.harleylizard.difficultyex.common.config.variable

import com.google.gson.JsonObject
import net.minecraft.world.entity.LivingEntity

sealed interface Global {
    val name: String

    fun invoke(entity: LivingEntity): Double

    fun serialise(entry: JsonObject)

    companion object {

        fun get(global: String, entry: JsonObject): Global {
            return when (global) {
                DistanceSpawn.NAME -> DistanceSpawn.instance
                PlayerAverage.NAME ->  PlayerAverage.averageOf(entry.getAsJsonPrimitive("range").asDouble)
                EntityLevel.NAME -> EntityLevel.instance
                else -> throw RuntimeException("unknown global $this")
            }
        }
    }

}