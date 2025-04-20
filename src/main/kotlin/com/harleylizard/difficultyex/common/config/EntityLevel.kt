package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.harleylizard.difficultyex.common.config.Scaling.Companion.scaling
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import java.lang.reflect.Type

class EntityLevel private constructor(
    private val filter: Filter,
    private val scaling: Scaling) {

    companion object {
        val serialiser = object : Serialiser<EntityLevel> {
            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): EntityLevel {
                return EntityLevel(
                    p2.deserialize(p0.asJsonObject.get("entities"), Filter::class.java),
                    p2.deserialize(p0, Scaling::class.java)
                )
            }

            override fun serialize(p0: EntityLevel, p1: Type, p2: JsonSerializationContext): JsonElement {
                val `object` = p2.serialize(p0.scaling, Scaling::class.java).asJsonObject
                `object`.add("entities", p2.serialize(p0.filter, Filter::class.java))
                return `object`
            }

        }

        fun EntityType<out Entity>.levelOf(range: IntRange) = EntityLevel(Filter.filterOf(this), range.scaling)

    }
}