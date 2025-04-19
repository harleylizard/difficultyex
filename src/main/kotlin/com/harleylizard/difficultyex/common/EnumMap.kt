package com.harleylizard.difficultyex.common

import com.google.gson.*
import java.lang.reflect.Type
import java.util.Collections

class EnumMap<T : Enum<T>> private constructor(private val map: Map<T, String>) :
    JsonDeserializer<T>,
    JsonSerializer<T> {

    private val inverse = map.inverse

    override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): T {
        return inverse[p0.asJsonPrimitive.asString] ?: throw RuntimeException()
    }

    override fun serialize(p0: T, p1: Type, p2: JsonSerializationContext): JsonElement {
        return map[p0]?.let(::JsonPrimitive) ?: throw RuntimeException()
    }

    companion object {
        private val <K, V> Map<K, V>.inverse get() = entries.associate { (key, value) -> value to key }

        fun <T : Enum<T>> enumMapOf(vararg pairs: Pair<T, String>) = EnumMap(Collections.unmodifiableMap(mapOf(*pairs)))

    }
}