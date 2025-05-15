package com.harleylizard.difficultyex.common.config.variable

import com.google.gson.JsonObject
import com.google.gson.JsonSerializer
import net.minecraft.world.entity.LivingEntity

class GlobalVariable private constructor(private val global: Global) : Variable {

    override fun invoke(entity: LivingEntity) = global.invoke(entity)

    companion object {
        const val NAME = "global"

        val Global.variable get() = GlobalVariable(this)

        val serialiser = JsonSerializer<GlobalVariable> { p0, p1, p2 ->
            val `object` = JsonObject()
            `object`.addProperty("source", NAME)
            `object`.addProperty("value", p0.global.name)
            p0.global.serialise(`object`)
            `object`
        }

    }
}