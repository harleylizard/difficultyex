package com.harleylizard.difficultyex.common.config.variable

import net.minecraft.world.entity.LivingEntity

class DistanceSpawn private constructor(): Global {
    override val name = NAME

    override fun invoke(entity: LivingEntity) = entity.level().sharedSpawnPos.distManhattan(entity.blockPosition()).toDouble()

    companion object {
        const val NAME = "distance_from_spawn"

        val instance = DistanceSpawn()

    }
}