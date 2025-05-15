package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.harleylizard.difficultyex.common.config.variable.Variables
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import java.lang.reflect.Type

class AttributeLeveler(
    private val attribute: Attribute,
    private val scaling: Scaling,
    private val filter2: Filter2) {

    fun apply(variables: Variables, entity: LivingEntity) {
        val attributes = entity.attributes
        if (attributes.hasAttribute(attribute)) {
            attributes.getInstance(attribute)?.baseValue = scaling.eval(variables, EntityQuery(variables, entity))
        }
    }

    companion object {
        val serialiser = object : Serialiser<AttributeLeveler> {
            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): AttributeLeveler {
                val `object` = p0.asJsonObject
                val scaling = p2.deserialize<Scaling>(`object`, Scaling::class.java)
                val attribute = p2.deserialize<Attribute>(`object`.get("attribute"), Attribute::class.java)
                val filter2 = p2.deserialize<Filter2>(`object`.get("filter"), Filter2::class.java)
                return AttributeLeveler(attribute, scaling, filter2)
            }

            override fun serialize(p0: AttributeLeveler, p1: Type, p2: JsonSerializationContext): JsonElement {
                val `object` = p2.serialize(p0.scaling, Scaling::class.java).asJsonObject
                `object`.add("attribute", p2.serialize(p0.attribute, Attribute::class.java))
                `object`.add("filter", p2.serialize(p0.filter2, Filter2::class.java))
                return `object`
            }

        }

    }
}