package com.harleylizard.difficultyex.common

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.harleylizard.difficultyex.common.config.DifficultyMode
import com.harleylizard.difficultyex.common.config.Scaling
import com.harleylizard.difficultyex.common.config.SourceExpression
import com.harleylizard.difficultyex.common.config.SubDifficultyMode
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
                .registerTypeAdapter(DifficultyMode::class.java, DifficultyMode.serialiser)
                .registerTypeAdapter(SubDifficultyMode::class.java, SubDifficultyMode.serialiser)
                .registerTypeAdapter(SourceExpression::class.java, SourceExpression.serialiser)
                .registerTypeAdapter(Scaling::class.java, Scaling.serialiser)
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