package com.harleylizard.difficultyex.common

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.resources.ResourceLocation

class DifficultyEx : ModInitializer {

    override fun onInitialize() {
        DifficultyExEntityTags.init()

        ServerWorldEvents.LOAD.register { server, level ->
            DifficultyExConfig.config
        }

    }

    companion object {
        private const val MOD_ID = "difficultyex"

        val String.resourceLocation get() = ResourceLocation(MOD_ID, this)

    }
}