import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.serialization)
}
val kspAndroid by configurations.creating
val kspIosSimulatorArm64 by configurations.creating
val kspIosX64 by configurations.creating
val kspIosArm64 by configurations.creating

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Database"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(projects.modelhub)
            implementation(libs.kotlinx.serialization)
        }
    }
}

android {
    namespace = "com.inspekt.database"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
room {
    schemaDirectory("$projectDir/schemas")

}
dependencies {
    commonMainApi(libs.room.runtime)
    commonMainApi(libs.sqlite.bundled)
    kspAndroid(libs.room.compiler)
    kspIosSimulatorArm64(libs.room.compiler)
    kspIosX64(libs.room.compiler)
    kspIosArm64(libs.room.compiler)
}