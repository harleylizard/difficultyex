import soul.software.snail.dependency.exclusiveMaven
import soul.software.snail.dependency.snail

plugins {
    id("soul.software.snail") version "3.3-SNAPSHOT"
}

group = "com.harleylizard"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()

    exclusiveMaven("https://maven.ladysnake.org/releases", "dev.onyxstudios.cardinal-components-api")
    exclusiveMaven("https://api.modrinth.com/maven", "maven.modrinth")
    maven("https://maven.wispforest.io/releases/")
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
        implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-chunk:$components")

        implementation("io.wispforest:owo-lib:0.11.2+1.20")
        implementation("io.wispforest:endec:0.1.7")
        implementation("io.wispforest.endec:netty:0.1.3")

        implementation("maven.modrinth:playerex-directors-cut:4.0.2+1.20.1")
        implementation("maven.modrinth:data-attributes-directors-cut:2.0.3+1.20.1-fabric")
        implementation("maven.modrinth:ranged-weapon-api:1.1.4+1.20.1")
        implementation("maven.modrinth:modmenu:7.2.2")
        implementation("maven.modrinth:additionalentityattributes:1.7.6+1.20.1")
        implementation("maven.modrinth:opc-directors-cut:2.0.0+1.20.1-beta.4-fabric")
        implementation("maven.modrinth:placeholder-api:2.1.4+1.20.1")
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
            named("cardinal-components").set(listOf("com.harleylizard.difficultyex.common.DifficultyExComponents"))
        }
        custom {
            it["cardinal-components"].addAll(listOf(
                "difficultyex:entity_level"))
        }
    }
}