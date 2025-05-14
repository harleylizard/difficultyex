package com.harleylizard.difficultyex.common.config.variable

import com.google.gson.JsonObject
import com.google.gson.JsonSerializer
import net.minecraft.world.entity.LivingEntity

class ConstantVariable private constructor(private val value: Double) : Variable {

    override fun invoke(entity: LivingEntity) = value

    companion object {
        const val NAME = "constant"

        val Double.variable get() = ConstantVariable(this)

        val serialiser = JsonSerializer<ConstantVariable> { p0, p1, p2 ->
            val `object` = JsonObject()
            `object`.addProperty("source", NAME)
            `object`.addProperty("value", p0.value)
            `object`
        }

    }
}