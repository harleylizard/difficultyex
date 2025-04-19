package com.harleylizard.difficultyex.common

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Files
import java.nio.file.Path

class DifficultyExConfig {
    val mode = DifficultyMode.PLAYER

    @SerializedName("sub_mode")
    val subMode = SubDifficultyMode.AGE

    companion object {
        val config = configOf(FabricLoader.getInstance().configDir.resolve("difficultyex.json"))

        private fun configOf(path: Path): DifficultyExConfig {
            val gson = GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(DifficultyMode::class.java, DifficultyMode.map)
                .registerTypeAdapter(SubDifficultyMode::class.java, SubDifficultyMode.map)
                .create()

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