package com.harleylizard.difficultyex.common.config.variable

import com.google.gson.JsonObject
import com.google.gson.JsonSerializer
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute

class AttributeVariable private constructor(private val attribute: Attribute) : Variable {

    override fun invoke(entity: LivingEntity) = entity.attributes.let {
        if (it.hasAttribute(attribute)) it.getValue(attribute) else 0.0
    }

    companion object {
        const val NAME = "entity_attribute"

        val Attribute.variable get() = AttributeVariable(this)

        val serialiser = JsonSerializer<AttributeVariable> { p0, p1, p2 ->
            val `object` = JsonObject()
            `object`.addProperty("source", NAME)
            `object`.addProperty("value", BuiltInRegistries.ATTRIBUTE.getKey(p0.attribute).toString())
            `object`
        }

    }
}