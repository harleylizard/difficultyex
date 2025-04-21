package com.harleylizard.difficultyex.common.config

import com.google.gson.*
import com.harleylizard.difficultyex.common.config.AttributeVariable.Companion.variable
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import java.lang.reflect.Type

sealed interface Variable {

    fun invoke(entity: LivingEntity): Double

    companion object {
        val serialiser = object : Serialiser<Variable> {
            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext?): Variable {
                val `object` = p0.asJsonObject

                val source = `object`.getAsJsonPrimitive("source").asString
                val value = `object`.getAsJsonPrimitive("value")

                return when (source) {
                    "const" -> ConstVariable(value.asDouble)
                    "entity_attribute" -> (BuiltInRegistries.ATTRIBUTE.get(ResourceLocation(value.asString)) ?: throw RuntimeException("Unknown attribute")).variable
                    else -> throw RuntimeException("")
                }
            }

            override fun serialize(p0: Variable, p1: Type, p2: JsonSerializationContext): JsonElement {
                return when (p0) {
                    is ConstVariable -> { p2.serialize(p1, ConstVariable::class.java) }
                    is AttributeVariable -> { p2.serialize(p0, AttributeVariable::class.java) }
                }
            }
        }
    }
}

class ConstVariable(private val value: Double) : Variable {

    override fun invoke(entity: LivingEntity) = value

    companion object {
        val serialiser = JsonSerializer<ConstVariable> { p0, p1, p2 ->
            val `object` = JsonObject()
            `object`.addProperty("source", "const")
            `object`.addProperty("value", p0.value)
            `object`
        }

    }
}

class AttributeVariable private constructor(private val attribute: Attribute) : Variable {

    override fun invoke(entity: LivingEntity) = entity.attributes.let {
        if (it.hasAttribute(attribute)) it.getValue(attribute) else 0.0
    }

    companion object {
        val Attribute.variable get() = AttributeVariable(this)

        val serialiser = JsonSerializer<AttributeVariable> { p0, p1, p2 ->
            val `object` = JsonObject()
            `object`.addProperty("source", "entity_attribute")
            `object`.addProperty("value", BuiltInRegistries.ATTRIBUTE.getKey(p0.attribute).toString())
            `object`
        }

    }
}