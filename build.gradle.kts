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
    exclusiveMaven("https://api.modrinth.com/maven", "maven.modrinth")

    //maven("https://maven.wispforest.io/releases/")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("net.objecthunter:exp4j:0.4.8")

    snail {
        val components = "5.2.3"
        implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-base:$components")
        implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:$components")
        implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-level:$components")
        implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-world:$components")

        //implementation("io.wispforest:owo-lib:0.11.2+1.20")

        //implementation("maven.modrinth:playerex-directors-cut:4.0.2+1.20.1")
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
        mixins = listOf("difficultyex.mixins.json")
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