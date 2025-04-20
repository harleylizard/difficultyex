package com.harleylizard.difficultyex.common.config

enum class DifficultyMode {
    PLAYER,
    LEVEL,
    DISTANCE;

    companion object {
        val serialiser = EnumSerialiser.enumSerialiserOf(
            PLAYER to "player",
            LEVEL to "level",
            DISTANCE to "distance"
        )

    }
}