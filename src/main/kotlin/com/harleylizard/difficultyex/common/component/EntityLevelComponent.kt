package com.harleylizard.difficultyex.common.component

import com.harleylizard.difficultyex.common.DifficultyExComponents
import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.LivingEntity

class EntityLevelComponent(private val entity: LivingEntity) : ComponentV3, AutoSyncedComponent {
    var level = 1

    override fun readFromNbt(compound: CompoundTag) {
        level = compound.getInt(LEVEL_NAME)
    }

    override fun writeToNbt(compound: CompoundTag) {
        compound.putInt(LEVEL_NAME, level)
    }

    companion object {
        private const val LEVEL_NAME = "Level"

        val LivingEntity.levelComponent: EntityLevelComponent get() = getComponent(DifficultyExComponents.entityLevel)



    }
}