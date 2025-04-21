package com.harleylizard.difficultyex.common

import com.harleylizard.difficultyex.common.DifficultyEx.Companion.resourceLocation
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType

object DifficultyExEntityTags {
    private val String.tag: TagKey<EntityType<out Entity>> get() = TagKey.create(Registries.ENTITY_TYPE, resourceLocation)

    val passive = "passive".tag

    fun init() {

    }

}