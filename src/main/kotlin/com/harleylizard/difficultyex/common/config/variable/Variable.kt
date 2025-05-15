package com.harleylizard.difficultyex.common.config.variable

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.harleylizard.difficultyex.common.config.Serialiser
import com.harleylizard.difficultyex.common.config.variable.AttributeVariable.Companion.variable
import com.harleylizard.difficultyex.common.config.variable.ConstantVariable.Companion.variable
import com.harleylizard.difficultyex.common.config.variable.GlobalVariable.Companion.variable
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import java.lang.reflect.Type

sealed interface Variable {

    fun invoke(entity: LivingEntity): Double

    companion object {

        val serialiser = object : Serialiser<Variable> {
            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): Variable {
                val `object` = p0.asJsonObject
                val source = `object`.getAsJsonPrimitive("source").asString
                val value = `object`.getAsJsonPrimitive("value")

                return when (source) {
                    ConstantVariable.NAME -> value.asDouble.variable
                    AttributeVariable.NAME -> {
                        val attribute = p2.deserialize<Attribute>(value, Attribute::class.java)
                        attribute.variable
                    }
                    GlobalVariable.NAME -> Global.get(value.asString, `object`).variable
                    else -> throw RuntimeException("")
                }
            }

            override fun serialize(p0: Variable, p1: Type, p2: JsonSerializationContext): JsonElement {
                return when (p0) {
                    is ConstantVariable -> { p2.serialize(p0, ConstantVariable::class.java) }
                    is AttributeVariable -> { p2.serialize(p0, AttributeVariable::class.java) }
                    is GlobalVariable -> { p2.serialize(p0, GlobalVariable::class.java) }
                }
            }
        }
    }
}