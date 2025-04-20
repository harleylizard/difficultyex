package com.harleylizard.difficultyex.common.config

import net.minecraft.world.entity.LivingEntity
import net.objecthunter.exp4j.Expression

typealias Query = (Expression) -> Expression

class EntityQuery(private val entity: LivingEntity) : Query {

    override fun invoke(p1: Expression) = p1

}