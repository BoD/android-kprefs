plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "org.jraf.android.kprefs.sample"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }
}

dependencies {
    implementation(kotlin("stdlib", Versions.KOTLIN))
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.security:security-crypto:1.1.0-alpha02")
    implementation(project(":library"))
}
