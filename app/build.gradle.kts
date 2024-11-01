@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.tasks.PackageAndroidArtifact

plugins {
    alias(libs.plugins.agp.app)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.lsplugin.apksign)
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")
}

android {
    namespace = "com.rxuglr.m3khelper"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.remtrik.m3khelper"
        minSdk = 29
        targetSdk = 34
        versionCode = 15
        versionName = "2.0.1"
        resourceConfigurations += listOf(
            "en", "ru", "ar"
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            isDebuggable = false
            isJniDebuggable = false
        }
    }
    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
        resources {
            // https://stackoverflow.com/a/58956288
            // It will break Layout Inspector, but it's unused for release build.
            excludes += "META-INF/*.version"
            // https://github.com/Kotlin/kotlinx.coroutines?tab=readme-ov-file#avoiding-including-the-debug-infrastructure-in-the-resulting-apk
            excludes += "DebugProbesKt.bin"
            // https://issueantenna.com/repo/kotlin/kotlinx.coroutines/issues/3158
            excludes += "kotlin-tooling-metadata.json"
        }
    }

    applicationVariants.all {
        outputs.forEach {
            val output = it as BaseVariantOutputImpl
            output.outputFileName = "M3K_Helper_${versionName}_${name}.apk"
        }
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.compose.destinations.animations.core)
    ksp(libs.compose.destinations.ksp)

    implementation(libs.com.github.topjohnwu.libsu.core)
    implementation(libs.com.github.topjohnwu.libsu.service)

    implementation(libs.io.coil.kt.coil.compose)

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.material)
}
