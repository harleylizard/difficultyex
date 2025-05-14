package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.harleylizard.difficultyex.common.config.SourceExpression.Companion.expression
import com.harleylizard.difficultyex.common.config.variable.Variables
import net.minecraft.util.Mth
import java.lang.reflect.Type

class Scaling private constructor(
    private val min: Double,
    private val max: Double,
    private val source: SourceExpression) {

    fun eval(variables: Variables, query: Query) = query(source.expression(variables)).evaluate().clamp(min, max)

    companion object {
        val IntRange.scaling get() = Scaling(
            first.coerceAtMost(last).toDouble(),
            first.coerceAtLeast(last).toDouble(), SourceExpression.empty)

        private fun Double.clamp(min: Double, max: Double) = Mth.clamp(this, min, max)

        val serialiser = object : Serialiser<Scaling> {

            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): Scaling {
                val `object` = p0.asJsonObject
                val source = if (`object`.has("expression"))
                    p2.deserialize(`object`.get("expression"), SourceExpression::class.java) else SourceExpression.empty
                return Scaling(
                    `object`.getAsJsonPrimitive("min").asDouble,
                    `object`.getAsJsonPrimitive("max").asDouble,
                    source)
            }

            override fun serialize(p0: Scaling, p1: Type, p2: JsonSerializationContext): JsonElement {
                val `object` = JsonObject()
                `object`.addProperty("min", p0.min)
                `object`.addProperty("max", p0.max)
                p0.source.takeUnless { it.empty }?.let {
                    `object`.add("expression", p2.serialize(p0.source))
                }
                return `object`
            }
        }

        fun IntRange.scalingOf(expression: String) = Scaling(
            first.coerceAtMost(last).toDouble(),
            first.coerceAtLeast(last).toDouble(),
            expression.expression
        )

    }
}