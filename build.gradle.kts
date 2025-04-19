import soul.software.snail.dependency.exclusiveMaven
import soul.software.snail.dependency.snail

plugins {
    id("soul.software.snail") version "3.3-SNAPSHOT"
}

group = "com.harleylizard"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    exclusiveMaven("https://maven.ladysnake.org/releases", "dev.onyxstudios.cardinal-components-api")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    snail {
        implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-base:5.2.3").include
        implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:5.2.3").include
        implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-level:5.2.3").include
        implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-world:5.2.3").include
    }
}

tasks.test {
    useJUnitPlatform()
}

snail {
    fabric("1.20.1") {
        name = "DifficultyEX"
        id = "difficultyex"
        version = "1.0-SNAPSHOT"
        description = "scaling difficulty"
        license = "MIT"
        entryPoints {
            main = listOf("com.harleylizard.difficultyex.common.DifficultyEx")
            client = listOf("com.harleylizard.difficultyex.client.DifficultyExClient")
        }
        custom {
            it["cardinal-components"].addAll(listOf(
                "difficultyex:level_difficulty",
                "difficultyex:player_difficulty"))
        }
    }
}