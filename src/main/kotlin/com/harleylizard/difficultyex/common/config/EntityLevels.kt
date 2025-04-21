package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.harleylizard.difficultyex.common.config.Scaling.Companion.scaling
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap
import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntMaps
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import java.lang.reflect.Type
import java.util.*

class EntityLevels private constructor(
    private val list: List<Level>,
    private val map: Object2IntMap<EntityType<out Entity>>) {

    operator fun get(entity: Entity) = list[map.getInt(entity.type)]

    companion object {
        val serialiser = object : Serialiser<EntityLevels> {

            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): EntityLevels {
                val list = mutableListOf<Level>()
                val map: Object2IntMap<EntityType<out Entity>> = Object2IntArrayMap()

                val array = p0.asJsonArray
                for (entry in array) {
                    val `object` = entry.asJsonObject
                    val filter = p2.deserialize<Filter>(`object`.get("entities"), Filter::class.java)
                    val scaling = p2.deserialize<Scaling>(`object`, Scaling::class.java)
                    val level = Level(filter, scaling)

                    list += level
                    val i = list.indexOf(level)
                    for (type in filter.entityTypes) {
                        map[type] = i
                    }
                }
                return EntityLevels(
                    Collections.unmodifiableList(list),
                    Object2IntMaps.unmodifiable(map))
            }

            override fun serialize(p0: EntityLevels, p1: Type, p2: JsonSerializationContext): JsonElement {
                val array = JsonArray()
                for (level in p0.list) {
                    val `object` = p2.serialize(level.scaling, Scaling::class.java).asJsonObject
                    `object`.add("entities", p2.serialize(level.filter, Filter::class.java))
                    array.add(`object`)
                }
                return array
            }
        }

        fun levelsOf(level: Level): EntityLevels {
            val list = listOf(level)
            val map: Object2IntMap<EntityType<out Entity>> = Object2IntArrayMap(1)
            for (type in level.filter.entityTypes) {
                map.put(type, 0)
            }
            return EntityLevels(Collections.unmodifiableList(list), Object2IntMaps.unmodifiable(map))
        }

        fun EntityType<out Entity>.levelOf(range: IntRange) = Level(Filter.filterOf(this), range.scaling)

        data class Level(val filter: Filter, val scaling: Scaling) {
        }
    }
}