package com.harleylizard.difficultyex.common

import com.google.common.base.Suppliers
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.harleylizard.difficultyex.common.config.*
import com.harleylizard.difficultyex.common.config.DifficultyExLevelConfig.Companion.serialiser
import com.harleylizard.difficultyex.common.config.variable.*
import net.fabricmc.loader.api.FabricLoader
import java.util.function.Supplier

class Configs {

    companion object {
        val gson: Gson = GsonBuilder()
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
            .setPrettyPrinting()
            .create()

        val leveling: Supplier<DifficultyExLevelConfig> = Suppliers.memoize { DifficultyExLevelConfig.configOf(FabricLoader.getInstance().configDir.resolve("difficultyex_leveling.json")) }
        val attributes: Supplier<DifficultyExAttributeConfig> = Suppliers.memoize { DifficultyExAttributeConfig.configOf(FabricLoader.getInstance().configDir.resolve("difficultyex_attributes.json")) }

    }

}