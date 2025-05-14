package com.harleylizard.difficultyex.common.config.variable

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.harleylizard.difficultyex.common.config.Serialiser
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.reflect.Type
import java.util.*

class Variables(private val map: Map<String, Variable>) : Iterable<Map.Entry<String, Variable>> {

    fun invoke(builder: ExpressionBuilder): ExpressionBuilder = builder.variables(map.keys)

    override fun iterator(): Iterator<Map.Entry<String, Variable>> = map.iterator()

    companion object {
        val serialiser = object : Serialiser<Variables> {

            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): Variables {
                val map = mutableMapOf<String, Variable>()
                for ((key, value) in p0.asJsonObject.entrySet()) {
                    map[key] = p2.deserialize(value, Variable::class.java)
                }
                return Variables(Collections.unmodifiableMap(map))
            }

            override fun serialize(p0: Variables, p1: Type, p2: JsonSerializationContext): JsonElement {
                val `object` = JsonObject()
                for ((key, value) in p0.map.entries) {
                    `object`.add(key, p2.serialize(value, Variable::class.java))
                }
                return `object`
            }

        }

        fun variablesOf(vararg args: Pair<String, Variable>) = Variables(Collections.unmodifiableMap(mapOf(*args)))

    }

}