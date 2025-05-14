package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap
import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntMaps
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import java.lang.reflect.Type
import java.util.*

class Levelers private constructor(
    private val list: List<Leveler>,
    private val map: Object2IntMap<EntityType<out Entity>>) {

    operator fun get(entity: Entity) = entity.type.takeIf { map.containsKey(it) }?.let { list[map.getInt(it)] }

    companion object {
        val serialiser = object : Serialiser<Levelers> {

            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): Levelers {
                val list = mutableListOf<Leveler>()
                val map: Object2IntMap<EntityType<out Entity>> = Object2IntArrayMap()

                val array = p0.asJsonArray
                for (entry in array) {
                    val `object` = entry.asJsonObject
                    val filter = p2.deserialize<Filter>(`object`.get("filter"), Filter::class.java)
                    val scaling = p2.deserialize<Scaling>(`object`, Scaling::class.java)
                    val leveler = Leveler(filter, scaling)

                    list += leveler
                    val i = list.indexOf(leveler)
                    for (type in filter.entityTypes) {
                        map[type] = i
                    }
                }
                return Levelers(
                    Collections.unmodifiableList(list),
                    Object2IntMaps.unmodifiable(map))
            }

            override fun serialize(p0: Levelers, p1: Type, p2: JsonSerializationContext): JsonElement {
                val array = JsonArray()
                for (leveler in p0.list) {
                    array.add(p2.serialize(leveler, Leveler::class.java))
                }
                return array
            }
        }

        fun levelsOf(leveler: Leveler): Levelers {
            val list = listOf(leveler)
            val map: Object2IntMap<EntityType<out Entity>> = Object2IntArrayMap(1)
            for (type in leveler.filter.entityTypes) {
                map.put(type, 0)
            }
            return Levelers(Collections.unmodifiableList(list), Object2IntMaps.unmodifiable(map))
        }
    }
}