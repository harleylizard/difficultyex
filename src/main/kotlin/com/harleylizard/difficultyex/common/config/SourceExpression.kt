package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.harleylizard.difficultyex.common.config.variable.Variables
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.reflect.Type

class SourceExpression private constructor(private val source: String) {
    val empty get() = source == ZERO

    fun expression(variables: Variables): Expression = variables.invoke(ExpressionBuilder(source)).build()

    companion object {
        private const val ZERO = "0.0"

        val empty = SourceExpression(ZERO)

        val String.expression get() = SourceExpression(this)

        val serialiser = object : Serialiser<SourceExpression> {
            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext) = SourceExpression(p0.asJsonPrimitive.asString)

            override fun serialize(p0: SourceExpression, p1: Type, p2: JsonSerializationContext) = JsonPrimitive(p0.source)
        }

    }

}