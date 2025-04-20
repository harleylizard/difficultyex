package com.harleylizard.difficultyex.common.config

enum class SubDifficultyMode {
    LEVEL,
    AGE;

    companion object {
        val serialiser = EnumSerialiser.enumSerialiserOf(
            LEVEL to "level",
            AGE to "age"
        )

    }
}