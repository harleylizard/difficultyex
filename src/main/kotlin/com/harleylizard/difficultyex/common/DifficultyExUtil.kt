package com.harleylizard.difficultyex.common

import net.minecraft.world.entity.LivingEntity
import kotlin.math.floor

object DifficultyExUtil {

    fun spawnWithLevel(entity: LivingEntity) {
        val key = DifficultyExComponents.entityLevel
        val component = entity.getComponent(key)
        val config = Configs.leveling.get()

        val leveler = config.levelers[entity] ?: config.fallback
        val level = floor(leveler.eval(config.variables, entity)).toInt()

        component.level = level
        entity.syncComponent(key)

        spawnWithAttributes(entity)
    }

    private fun spawnWithAttributes(entity: LivingEntity) {
        val config = Configs.attributes.get()

        for (leveler in config.levelers) {
            leveler.apply(config.variables, entity)
        }
        val maxHealth = entity.maxHealth
               entity.heal(maxHealth)
    }
}