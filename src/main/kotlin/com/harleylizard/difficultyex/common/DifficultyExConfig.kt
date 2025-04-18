package com.harleylizard.difficultyex.common

import com.google.gson.GsonBuilder
import java.nio.file.Files
import java.nio.file.Path

class DifficultyExConfig {
    val mode = DifficultyMode.PLAYER

    companion object {

        fun configOf(path: Path): DifficultyExConfig {
            val gson = GsonBuilder().create()
            return path.takeIf(Files::isRegularFile)?.let {
                Files.newBufferedReader(path).use { reader ->
                    gson.fromJson(reader, DifficultyExConfig::class.java)
                }
            } ?: Files.newBufferedWriter(path).use {
                val default = DifficultyExConfig()
                gson.toJson(default, it)
                default
            }
        }
    }
}