package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.harleylizard.difficultyex.common.config.Scaling.Companion.scaling
import com.harleylizard.difficultyex.common.config.Scaling.Companion.scalingOf
import com.harleylizard.difficultyex.common.config.variable.Variables
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import java.lang.reflect.Type

data class Leveler(val filter: Filter, val scaling: Scaling) {

    fun eval(variables: Variables, entity: LivingEntity) = scaling.eval(variables, EntityQuery(variables, entity))

    companion object {
        val serialiser = object : Serialiser<Leveler> {
            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): Leveler {
                val `object` = p0.asJsonObject
                val filter = p2.deserialize<Filter>(`object`.get("filter"), Filter::class.java)
                val scaling = p2.deserialize<Scaling>(`object`, Scaling::class.java)
                return Leveler(filter, scaling)
            }

            override fun serialize(p0: Leveler, p1: Type, p2: JsonSerializationContext): JsonElement {
                val `object` = p2.serialize(p0.scaling, Scaling::class.java).asJsonObject
                `object`.add("filter", p2.serialize(p0.filter, Filter::class.java))
                return `object`
            }

        }

        val IntRange.leveler get() = Leveler(Filter.empty, scaling)

        fun EntityType<out Entity>.levelerOf(range: IntRange) = Leveler(Filter.filterOf(this), range.scaling)

        fun EntityType<out Entity>.levelerOf(range: IntRange, expression: String) = Leveler(Filter.filterOf(this), range.scalingOf(expression))

        fun IntRange.levelerOf(expression: String) = Leveler(Filter.empty, scalingOf(expression))

    }
}
