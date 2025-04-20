package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type
import java.util.*

class EnumSerialiser<T : Enum<T>> private constructor(private val map: Map<T, String>) : Serialiser<T> {
    private val inverse = map.inverse

    override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): T {
        return inverse[p0.asJsonPrimitive.asString] ?: throw RuntimeException()
    }

    override fun serialize(p0: T, p1: Type, p2: JsonSerializationContext): JsonElement {
        return map[p0]?.let(::JsonPrimitive) ?: throw RuntimeException()
    }

    companion object {
        private val <K, V> Map<K, V>.inverse get() = entries.associate { (key, value) -> value to key }

        fun <T : Enum<T>> enumSerialiserOf(vararg pairs: Pair<T, String>) = EnumSerialiser(Collections.unmodifiableMap(mapOf(*pairs)))

    }
}