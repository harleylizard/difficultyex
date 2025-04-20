package com.harleylizard.difficultyex.common

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.harleylizard.difficultyex.common.config.*
import com.harleylizard.difficultyex.common.config.EntityLevel.Companion.levelOf
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.world.entity.EntityType
import java.nio.file.Files
import java.nio.file.Path

class DifficultyExConfig {
    val mode = DifficultyMode.DISTANCE

    @SerializedName("sub_mode")
    val subMode = SubDifficultyMode.LEVEL

    @SerializedName("scale_whitelist")
    val scaleWhitelist = Filter.filterOf(DifficultyExEntityTags.passive)

    @SerializedName("entity_levels")
    val entityLevels = listOf(EntityType.PIG.levelOf(1 .. 100))

    companion object {
        val config = configOf(FabricLoader.getInstance().configDir.resolve("difficultyex.json"))

        private fun configOf(path: Path): DifficultyExConfig {
            val gson = GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(DifficultyMode::class.java, DifficultyMode.serialiser)
                .registerTypeAdapter(SubDifficultyMode::class.java, SubDifficultyMode.serialiser)
                .registerTypeAdapter(SourceExpression::class.java, SourceExpression.serialiser)
                .registerTypeAdapter(Scaling::class.java, Scaling.serialiser)
                .registerTypeAdapter(Filter::class.java, Filter.serialiser)
                .registerTypeAdapter(EntityLevel::class.java, EntityLevel.serialiser)
                .create()

            return path.takeIf(Files::isRegularFile)?.let {
                Files.newBufferedReader(path).use { reader ->
                    gson.fromJson(reader, DifficultyExConfig::class.java)
                }
            } ?: Files.newBufferedWriter(path).use {
                val default = DifficultyExConfig()
                gson.toJson(default, it)
                default
            }
        }
    }
}