package com.harleylizard.difficultyex.common

import net.fabricmc.api.ModInitializer
import net.minecraft.resources.ResourceLocation

class DifficultyEx : ModInitializer {

    override fun onInitialize() {
    }

    companion object {
        private const val MOD_ID = "difficultyex"

        val String.resourceLocation get() = ResourceLocation(MOD_ID, this)

    }
}