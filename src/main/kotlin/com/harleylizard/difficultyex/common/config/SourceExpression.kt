package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.reflect.Type

class SourceExpression private constructor(private val source: String) {
    val expression: Expression = ExpressionBuilder(source).build()

    companion object {
        val serialiser = object : Serialiser<SourceExpression> {
            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext) = SourceExpression(p0.asJsonPrimitive.asString)

            override fun serialize(p0: SourceExpression, p1: Type, p2: JsonSerializationContext) = JsonPrimitive(p0.source)
        }

    }

}