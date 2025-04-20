package com.harleylizard.difficultyex.common.config

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer

interface Serialiser<T> : JsonDeserializer<T>, JsonSerializer<T> {
}