package com.harleylizard.difficultyex.common.component

import dev.onyxstudios.cca.api.v3.component.ComponentV3

sealed interface HasDifficulty : ComponentV3 {
    val enabled: Boolean

}