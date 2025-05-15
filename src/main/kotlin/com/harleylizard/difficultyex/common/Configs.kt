package com.harleylizard.difficultyex.common

import com.google.common.base.Suppliers
import com.google.gson.*
import com.harleylizard.difficultyex.common.config.*
import com.harleylizard.difficultyex.common.config.DifficultyExLevelConfig.Companion.serialiser
import com.harleylizard.difficultyex.common.config.variable.*
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.ai.attributes.Attribute
import java.lang.reflect.Type
import java.util.function.Supplier

class Configs {

    companion object {
        private val attribute = object : Serialiser<Attribute> {
            override fun deserialize(p0: JsonElement, p1: Type, p2: JsonDeserializationContext): Attribute {
                val key = ResourceLocation(p0.asString)
                return BuiltInRegistries.ATTRIBUTE.get(key) ?: throw RuntimeException("Unknown attribute $key")
            }

            override fun serialize(p0: Attribute, p1: Type, p2: JsonSerializationContext): JsonElement {
                return JsonPrimitive(BuiltInRegistries.ATTRIBUTE.getKey(p0).toString())
            }

        }

        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(Attribute::class.java, attribute)
            .registerTypeAdapter(DifficultyExLevelConfig::class.java, serialiser)
            .registerTypeAdapter(DifficultyExAttributeConfig::class.java, DifficultyExAttributeConfig.serialiser)
            .registerTypeAdapter(Variables::class.java, Variables.serialiser)
            .registerTypeAdapter(Variable::class.java, Variable.serialiser)
            .registerTypeAdapter(GlobalVariable::class.java, GlobalVariable.serialiser)
            .registerTypeAdapter(AttributeVariable::class.java, AttributeVariable.serialiser)
            .registerTypeAdapter(ConstantVariable::class.java, ConstantVariable.serialiser)
            .registerTypeAdapter(Levelers::class.java, Levelers.serialiser)
            .registerTypeAdapter(Filter::class.java, Filter.serialiser)
            .registerTypeAdapter(Scaling::class.java, Scaling.serialiser)
            .registerTypeAdapter(SourceExpression::class.java, SourceExpression.serialiser)
            .registerTypeAdapter(Leveler::class.java, Leveler.serialiser)
            .registerTypeAdapter(Filter2::class.java, Filter2.serialiser)
            .registerTypeAdapter(AttributeLeveler::class.java, AttributeLeveler.serialiser)
            .setPrettyPrinting()
            .create()

        val leveling: Supplier<DifficultyExLevelConfig> = Suppliers.memoize { DifficultyExLevelConfig.configOf(FabricLoader.getInstance().configDir.resolve("difficultyex_leveling.json")) }
        val attributes: Supplier<DifficultyExAttributeConfig> = Suppliers.memoize { DifficultyExAttributeConfig.configOf(FabricLoader.getInstance().configDir.resolve("difficultyex_attributes.json")) }

    }

}