import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.github.ben-manes.versions") version Versions.BEN_MANES_VERSIONS_PLUGIN
}

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build", "gradle", Versions.ANDROID_GRADLE_PLUGIN)
        classpath(kotlin("gradle-plugin", Versions.KOTLIN))
        classpath("com.jfrog.bintray.gradle", "gradle-bintray-plugin", Versions.BINTRAY_PUBLISH_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }

    wrapper {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = Versions.GRADLE
    }

    // Configuration for gradle-versions-plugin
    withType<DependencyUpdatesTask> {
        resolutionStrategy {
            componentSelection {
                all {
                    if (setOf(
                            "alpha",
                            "beta",
                            "rc",
                            "preview",
                            "eap",
                            "m1"
                        ).any { candidate.version.contains(it, true) }
                    ) {
                        reject("Non stable")
                    }
                }
            }
        }
    }
}
