import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    `java-library`
    id("org.spongepowered.gradle.plugin") version "2.1.1"
    id ("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "oasis"
version = "1.0"

repositories {
    mavenCentral()
}

sponge {
    apiVersion("9.1.0-SNAPSHOT")
    license("All-Rights-Reserved")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    plugin("economyx") {
        displayName("EconomyX")
        entrypoint("oasis.economyx.EconomyX")
        description("An overpowered economy plugin for geeks.")
        links {
            source("https://github.com/themrsung/EconomyX")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

val javaTarget = 17 // Sponge targets a minimum of Java 17
java {
    sourceCompatibility = JavaVersion.toVersion(javaTarget)
    targetCompatibility = JavaVersion.toVersion(javaTarget)
    if (JavaVersion.current() < JavaVersion.toVersion(javaTarget)) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(javaTarget))
    }
}
dependencies {
    implementation("joda-time:joda-time:2.12.5")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda:2.14.2")
}

tasks.withType(JavaCompile::class).configureEach {
    options.apply {
        encoding = "utf-8" // Consistent source file encoding
        if (JavaVersion.current().isJava10Compatible) {
            release.set(javaTarget)
        }
    }
}

// Make sure all tasks which produce archives (jar, sources jar, javadoc jar, etc) produce more consistent output
tasks.withType(AbstractArchiveTask::class).configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}
