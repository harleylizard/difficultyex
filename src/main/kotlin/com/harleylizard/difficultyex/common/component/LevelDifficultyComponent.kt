package com.harleylizard.difficultyex.common.component

import com.harleylizard.difficultyex.common.DifficultyExConfig
import com.harleylizard.difficultyex.common.DifficultyMode
import net.minecraft.nbt.CompoundTag

class LevelDifficultyComponent : HasDifficulty {
    override val enabled get() = DifficultyExConfig.config.mode == DifficultyMode.LEVEL

    override fun readFromNbt(p0: CompoundTag) {
        TODO("Not yet implemented")
    }

    override fun writeToNbt(p0: CompoundTag) {
        TODO("Not yet implemented")
    }
}