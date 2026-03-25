import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.3.0"
    id("net.fabricmc.fabric-loom") version "1.15-SNAPSHOT"
    id("maven-publish")
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
    archivesName.set(project.property("archives_base_name") as String)
}

tasks.register("collectFile") {
    group = "build"
    mustRunAfter("build")

    doLast {
        copy {
            from(
                file(
                    "build/libs/${project.property("archives_base_name")}-${project.property("mod_version")}+${
                        project.property(
                            "minecraft_version"
                        )
                    }.jar"
                )
            )
            into(rootProject.file("build/libs"))
        }
        copy {
            from(
                file(
                    "build/libs/${project.property("archives_base_name")}-${project.property("mod_version")}+${
                        project.property(
                            "minecraft_version"
                        )
                    }-sources.jar"
                )
            )
            into(rootProject.file("build/libs"))
        }
    }
}

tasks.register("buildAndCollect") {
    group = "build"
    dependsOn(tasks.named("build"), tasks.named("collectFile"))
}


val targetJavaVersion = 25
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}



repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
    maven("https://api.modrinth.com/maven") {
        name = "Modrinth"
    }
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    implementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

    implementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")

    // Config UI (replaces Cloth Config)
    implementation("maven.modrinth:yacl:${project.property("yacl_version")}")

    // ModMenu integration (optional, but used to expose config screen)
    // NOTE: keep Mod Menu off the dev runtime classpath because its versions are tightly coupled
    // to specific 26.1 snapshots/pre-releases and may crash the game if mismatched.
    compileOnly("maven.modrinth:modmenu:${project.property("modmenu_version")}")
}

tasks.processResources {
    val mcVer = project.property("minecraft_version").toString().replace("-snapshot-", "-alpha.")
    inputs.property("version", project.version)
    inputs.property("minecraft_version", mcVer)
    inputs.property("loader_version", project.property("loader_version"))
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version.toString(),
            "minecraft_version" to mcVer,
            "loader_version" to project.property("loader_version").toString()
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    // If Javadoc is generated, this must be specified in that task too.
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName.get()}" }
    }
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.property("archives_base_name") as String
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
