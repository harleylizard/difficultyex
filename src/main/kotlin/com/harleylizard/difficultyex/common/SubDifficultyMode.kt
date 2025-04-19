package com.harleylizard.difficultyex.common

enum class SubDifficultyMode {
    LEVEL,
    AGE;

    companion object {
        val map = EnumMap.enumMapOf(
            LEVEL to "level",
            AGE to "age")

    }
}