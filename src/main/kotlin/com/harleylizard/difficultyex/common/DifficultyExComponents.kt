package com.harleylizard.difficultyex.common

import com.harleylizard.difficultyex.common.DifficultyEx.Companion.resourceLocation
import com.harleylizard.difficultyex.common.component.EntityLevelComponent
import dev.onyxstudios.cca.api.v3.component.ComponentKey
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer
import net.minecraft.world.entity.LivingEntity
import kotlin.reflect.KClass

class DifficultyExComponents : EntityComponentInitializer, LevelComponentInitializer {

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        registry.registerFor(LivingEntity::class.java, entityLevel, ::EntityLevelComponent)
    }

    override fun registerLevelComponentFactories(registry: LevelComponentFactoryRegistry) {
    }

    companion object {
        val entityLevel = register("entity_level", EntityLevelComponent::class)

        private fun <T : ComponentV3> register(name: String, kClass: KClass<T>): ComponentKey<T> {
            return ComponentRegistry.getOrCreate(name.resourceLocation, kClass.java)
        }
    }
}