import com.jfrog.bintray.gradle.BintrayExtension.PackageConfig
import com.jfrog.bintray.gradle.BintrayExtension.VersionConfig

plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
    id("com.jfrog.bintray")
}

group = "org.jraf"
version = "1.5.0"
description = "kprefs"

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(19)
        targetSdkVersion(30)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    implementation(kotlin("stdlib", Versions.KOTLIN))
    api("androidx.lifecycle:lifecycle-livedata:${Versions.ANDROIDX_LIFECYCLE_LIVEDATA}")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}")
}

afterEvaluate {
    android.libraryVariants.forEach { variant ->
        task<Jar>("jar${variant.name.capitalize()}Sources") {
            description = "Generate a sources Jar for ${variant.name}."
            group = "Publishing"
            archiveClassifier.set("sources")
            from(variant.sourceSets.map { it.javaDirectories })
        }
    }

    publishing {
        publications {
            create<MavenPublication>("releaseMavenPublication") {
                from(components["release"])
                artifactId = description
                artifact(tasks["jarReleaseSources"])
            }
        }
    }

    bintray {
        user = System.getenv("USER")
        key = System.getenv("KEY")
        setPublications("releaseMavenPublication")
        pkg(delegateClosureOf<PackageConfig> {
            repo = "JRAF"
            name = "KPrefs"
            userOrg = "bod"
            setLicenses("Apache-2.0")
            vcsUrl = "https://github.com/BoD/android-kprefs"
            version(delegateClosureOf<VersionConfig> {
                name = project.version.toString()
            })
        })
        publish = true
    }
}

// Use "./gradlew publishToMavenLocal" to deploy the artifacts to your local maven repository
// Use "USER=<username> KEY=<key> ./gradlew publishToMavenLocal bintrayUpload" to deploy the artifacts to bintray
// key can be found here: https://bintray.com/profile/edit and click on "API key"
