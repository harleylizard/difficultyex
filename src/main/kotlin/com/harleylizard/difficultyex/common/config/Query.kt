package com.harleylizard.difficultyex.common.config

import com.harleylizard.difficultyex.common.config.variable.Variables
import net.minecraft.world.entity.LivingEntity
import net.objecthunter.exp4j.Expression

typealias Query = (Expression) -> Expression

class EntityQuery(
    private val variables: Variables,
    private val entity: LivingEntity) : Query {

    override fun invoke(p1: Expression): Expression {
        var result = p1
        for ((key, value) in variables) {
            result = result.setVariable(key, value.invoke(entity))
        }
        return result
    }

}