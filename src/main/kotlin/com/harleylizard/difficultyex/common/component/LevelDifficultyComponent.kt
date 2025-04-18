package com.harleylizard.difficultyex.common.component

import net.minecraft.nbt.CompoundTag

class LevelDifficultyComponent : HasDifficulty {
    override val enabled get() = false

    override fun readFromNbt(p0: CompoundTag) {
        TODO("Not yet implemented")
    }

    override fun writeToNbt(p0: CompoundTag) {
        TODO("Not yet implemented")
    }
}