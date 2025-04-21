package com.harleylizard.difficultyex.common

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.harleylizard.difficultyex.common.config.*
import com.harleylizard.difficultyex.common.config.AttributeVariable.Companion.variable
import com.harleylizard.difficultyex.common.config.ConstVariable.Companion.variable
import com.harleylizard.difficultyex.common.config.EntityLevels.Companion.levelOf
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.tags.EntityTypeTags
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attributes
import java.nio.file.Files
import java.nio.file.Path

class DifficultyExConfig {
    val mode = DifficultyMode.DISTANCE

    @SerializedName("sub_mode")
    val subMode = SubDifficultyMode.LEVEL

    @SerializedName("scale_whitelist")
    val scaleWhitelist = Filter.empty

    @SerializedName("scale_blacklist")
    val scaleBlacklist = Filter.filterOf(DifficultyExEntityTags.passive)

    val variables = Variables.variablesOf(
        "level" to 5.0.variable,
        "max_health" to Attributes.MAX_HEALTH.variable,
        "armor" to Attributes.ARMOR.variable,
        "armor_toughness" to Attributes.ARMOR_TOUGHNESS.variable
    )

    @SerializedName("entity_levels")
    val entityLevels = EntityLevels.levelsOf(EntityType.PIG.levelOf(1 .. 100))

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
                .registerTypeAdapter(EntityLevels::class.java, EntityLevels.serialiser)
                .registerTypeAdapter(EntityLevels::class.java, EntityLevels.serialiser)
                .registerTypeAdapter(Variables::class.java, Variables.serialiser)
                .registerTypeAdapter(Variable::class.java, Variable.serialiser)
                .registerTypeAdapter(ConstVariable::class.java, ConstVariable.serialiser)
                .registerTypeAdapter(AttributeVariable::class.java, AttributeVariable.serialiser)
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