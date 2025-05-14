package com.harleylizard.difficultyex.common.config.variable

import net.minecraft.world.entity.LivingEntity

sealed interface Global {
    val name: String

    fun invoke(entity: LivingEntity): Double

    companion object {

        val String.global: Global get() = when (this) {
            DistanceSpawn.NAME -> DistanceSpawn.instance
            PlayerAverage.NAME ->  DistanceSpawn.instance
            EntityLevel.NAME -> EntityLevel.instance
            else -> throw RuntimeException("unknown global $this")
        }
    }

}