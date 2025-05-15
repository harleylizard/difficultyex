package com.harleylizard.difficultyex.common.config

import com.google.gson.*
import com.harleylizard.difficultyex.common.Configs
import com.harleylizard.difficultyex.common.DifficultyExEntityTags
import com.harleylizard.difficultyex.common.config.Scaling.Companion.scalingOf
import com.harleylizard.difficultyex.common.config.variable.AttributeVariable.Companion.variable
import com.harleylizard.difficultyex.common.config.variable.EntityLevel
import com.harleylizard.difficultyex.common.config.variable.GlobalVariable.Companion.variable
import com.harleylizard.difficultyex.common.config.variable.Variables
import net.minecraft.world.entity.ai.attributes.Attributes
import java.lang.reflect.Type
import java.nio.file.Files
import java.nio.file.Path

class DifficultyExAttributeConfig(
    val include: Filter,
    val exclude: Filter,
    val variables: Variables,
    val levelers: List<AttributeLeveler>) {

    companion object {
        val serialiser = object : Serialiser<DifficultyExAttributeConfig> {
            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): DifficultyExAttributeConfig {
                val `object` = p0.asJsonObject
                val include = Filter.serialiser.deserialize(`object`.get("include"), p1, p2)
                val exclude = Filter.serialiser.deserialize(`object`.get("exclude"), p1, p2)
                val variables = Variables.serialiser.deserialize(`object`.get("variables"), p1, p2)

                val levelers = mutableListOf<AttributeLeveler>()
                for (entry in `object`.getAsJsonArray("levelers")) {
                    levelers += p2.deserialize<AttributeLeveler>(entry, AttributeLeveler::class.java)
                }
                return DifficultyExAttributeConfig(include, exclude, variables, levelers)
            }

            override fun serialize(p0: DifficultyExAttributeConfig, p1: Type, p2: JsonSerializationContext): JsonElement {
                val `object` = JsonObject()
                `object`.add("include", Filter.serialiser.serialize(p0.include, p1, p2))
                `object`.add("exclude", Filter.serialiser.serialize(p0.exclude, p1, p2))
                `object`.add("variables", Variables.serialiser.serialize(p0.variables, p1, p2))

                val entries = JsonArray()
                for (entry in p0.levelers) {
                    entries.add(p2.serialize(entry, AttributeLeveler::class.java))
                }
                `object`.add("levelers", entries)

                return `object`
            }
        }

        private val defaultConfig get() = DifficultyExAttributeConfig(
            Filter.empty,
            Filter.filterOf(DifficultyExEntityTags.passive),
            Variables.variablesOf(
                "entity_level" to EntityLevel.instance.variable,
                "max_health" to Attributes.MAX_HEALTH.variable,
                "armor" to Attributes.ARMOR.variable,
                "armor_toughness" to Attributes.ARMOR_TOUGHNESS.variable
            ),
            listOf(
                AttributeLeveler(Attributes.MAX_HEALTH, (1 .. 100).scalingOf(
                    "(entity_level / (max_health / ((max_health - (max_health / 1.025) + 1)))) * max_health / 1.5"),
                    Filter2(Filter.empty, Filter.empty))
            )
        )

        fun configOf(path: Path): DifficultyExAttributeConfig {
            val gson = Configs.gson
            return path.takeIf(Files::isRegularFile)?.let {
                Files.newBufferedReader(path).use { reader ->
                    gson.fromJson(reader, DifficultyExAttributeConfig::class.java)
                }
            } ?: Files.newBufferedWriter(path).use {
                val default = defaultConfig
                gson.toJson(default, it)
                default
            }
        }
    }
}