import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val minecraft = "1.21.8"

plugins {
    kotlin("jvm")                     version "2.4.10"
    id("com.gradleup.shadow")         version "9.6.1"

    id("de.eldoria.plugin-yml.paper") version "0.9.0"
    id("xyz.jpenilla.run-paper")      version "3.1.0"

    id("maven-publish")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    mavenLocal()
}

dependencies {
    compileOnly("dev.folia:folia-api:$minecraft-R0.1-SNAPSHOT")

    paperLibrary("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

paper {
    authors = listOf("mirashz (mozarez)")
    website = "https://discord.gg/NRKHFvtEZ3"

    load    = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main    = "me.mozarez.aurora.minetech.MineTech"
    loader  = "me.mozarez.aurora.minetech.MineTechLoader"

    apiVersion            = minecraft
    generateLibrariesJson = true
    foliaSupported        = true

    serverDependencies {
        register("ARPLib") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
    }
}

kotlin {
    jvmToolchain(25)
}

version = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy.L.d"))

runPaper.folia.registerTask()

tasks {
    runServer {
        minecraftVersion(minecraft)
    }
    generatePaperPluginDescription {
        repos.set(repos.get().filterValues { !it.startsWith("file:") })
        useDefaultCentralProxy()
    }
    shadowJar {
        archiveClassifier.set("")
        destinationDirectory.set(file("$rootDir/dist"))
        minimize()
    }
    build {
        dependsOn(shadowJar)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["shadow"])
        }
    }
}
