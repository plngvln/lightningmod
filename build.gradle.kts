plugins {
    id("net.neoforged.moddev") version "2.0.147"
    id("maven-publish")
}

val modVersion = project.property("mod_version") as String
version = modVersion
group = project.property("maven_group") as String
val minecraftVersion = providers.gradleProperty("minecraft_version").get()

base {
    archivesName.set(project.property("archives_base_name") as String)
}

val targetJavaVersion = 25
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    withSourcesJar()
}

neoForge {
    version = project.property("neoforge_version") as String
    validateAccessTransformers = true

    runs {
        create("client") { client() }
        create("server") { server() }
    }

    mods {
        create("lightningmod") {
            sourceSet(sourceSets.main.get())
        }
    }
}

tasks.processResources {
    inputs.property("version", modVersion)
    inputs.property("minecraft_version", minecraftVersion)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(
            "version" to modVersion,
            "minecraft_version" to minecraftVersion
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName.get()}" }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.property("archives_base_name") as String
            from(components["java"])
        }
    }
}
