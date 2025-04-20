package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import net.minecraft.util.Mth
import java.lang.reflect.Type

class Scaling(
    private val min: Double,
    private val max: Double,
    private val source: SourceExpression) {

    fun eval(query: Query) = query(source.expression).evaluate().clamp(min, max)

    companion object {
        private fun Double.clamp(min: Double, max: Double) = Mth.clamp(this, min, max)

        val serialiser = object : Serialiser<Scaling> {

            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): Scaling {
                val obj = p0.asJsonObject
                val source = p2.deserialize<SourceExpression>(obj.get("expression"), SourceExpression::class.java)
                return Scaling(
                    obj.getAsJsonPrimitive("min").asDouble,
                    obj.getAsJsonPrimitive("max").asDouble, source)
            }

            override fun serialize(p0: Scaling, p1: Type, p2: JsonSerializationContext): JsonElement {
                val obj = JsonObject()
                obj.addProperty("min", p0.min)
                obj.addProperty("max", p0.max)
                obj.add("expression", p2.serialize(p0.source))
                return obj
            }

        }

    }
}