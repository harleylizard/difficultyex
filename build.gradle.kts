import soul.software.snail.dependency.snail

plugins {
    id("soul.software.snail") version "3.2-SNAPSHOT"
}

group = "com.harleylizard"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

snail {
    fabric("1.20.1") {
        name = "DifficultyEX"
        id = "difficultyex"
        version = "1.0-SNAPSHOT"
        description = "e"
        entryPoints {
            main = listOf("com.harleylizard.difficultyex.common.DifficultyEx")
            client = listOf("com.harleylizard.difficultyex.client.DifficultyExClient")
        }
    }
}