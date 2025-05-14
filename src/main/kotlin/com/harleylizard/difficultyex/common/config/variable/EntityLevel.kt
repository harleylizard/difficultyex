package com.harleylizard.difficultyex.common.config.variable

import com.harleylizard.difficultyex.common.component.EntityLevelComponent.Companion.levelComponent
import net.minecraft.world.entity.LivingEntity

class EntityLevel private constructor() : Global {
    override val name = NAME

    override fun invoke(entity: LivingEntity) = entity.levelComponent.level.toDouble()

    companion object {
        const val NAME = "entity_level"

        val instance = EntityLevel()

    }
}