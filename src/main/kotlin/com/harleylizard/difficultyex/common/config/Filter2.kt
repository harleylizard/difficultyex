package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type

class Filter2(
    private val include: Filter,
    private val exclude: Filter) {

    val filter = include - exclude

    companion object {
        val serialiser = object : Serialiser<Filter2> {
            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): Filter2 {
                val `object` = p0.asJsonObject
                val include = p2.deserialize<Filter>(`object`.get("include"), Filter::class.java)
                val exclude = p2.deserialize<Filter>(`object`.get("exclude"), Filter::class.java)
                return Filter2(include, exclude)
            }

            override fun serialize(p0: Filter2, p1: Type, p2: JsonSerializationContext): JsonElement {
                val `object` = JsonObject()
                `object`.add("include", p2.serialize(p0.include, Filter::class.java))
                `object`.add("exclude", p2.serialize(p0.exclude, Filter::class.java))
                return `object`
            }
        }

    }
}