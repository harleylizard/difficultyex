package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import java.lang.reflect.Type
import java.util.*

class Filter private constructor(
    private val types: List<EntityType<out Entity>>,
    private val tags: List<TagKey<EntityType<out Entity>>>) {

    val entityTypes get() = BuiltInRegistries.ENTITY_TYPE.filter { tags.any { tag -> it.`is`(tag) } } + types

    operator fun contains(entity: Entity) = entity.type in types || tags.any { entity `is` it }

    operator fun contains(type: EntityType<out Entity>) = type in types || tags.any { type.`is`(it) }

    companion object {
        val empty = Filter(emptyList(), emptyList())

        private infix fun Entity.`is`(tag: TagKey<EntityType<out Entity>>) = type.`is`(tag)

        val serialiser = object : Serialiser<Filter> {

            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): Filter {
                val types = mutableListOf<EntityType<out Entity>>()
                val tags = mutableListOf<TagKey<EntityType<out Entity>>>()

                for (element in p0.asJsonArray) {
                    val name = element.asJsonPrimitive.asString

                    if (name.startsWith("#")) {
                        val key = ResourceLocation(name.substring(1))
                        BuiltInRegistries.ENTITY_TYPE.tags.map { it.first }.filter {
                            it.location.equals(key)
                        }.findFirst().ifPresent {
                            tags += it
                        }
                        continue
                    }
                    BuiltInRegistries.ENTITY_TYPE[ResourceLocation(name)].let {
                        types += it
                    }
                }

                return Filter(
                    Collections.unmodifiableList(types),
                    Collections.unmodifiableList(tags))
            }

            override fun serialize(p0: Filter, p1: Type, p2: JsonSerializationContext): JsonElement {
                val array = JsonArray()
                for (type in p0.types) {
                    array.add(BuiltInRegistries.ENTITY_TYPE.getKey(type).toString())
                }
                for (tag in p0.tags) {
                    array.add("#${tag.location}")
                }
                return array
            }

        }

        fun filterOf(type: EntityType<out Entity>) = Filter(listOf(type), emptyList())

        fun filterOf(tag: TagKey<EntityType<out Entity>>) = Filter(emptyList(), listOf(tag))

    }
}