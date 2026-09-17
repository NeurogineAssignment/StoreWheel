import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.hilt.android.plugin)
}

android {
    namespace = "com.example.storewheel"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.storewheel"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Android framework utilities
    implementation(libs.androidx.core.ktx)
    // Kotlin extensions
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    // UI elements
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.swipe.to.refresh)
    // Lifecycle and kotlin flow
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // GLide
    implementation(libs.glide)
    // Coroutines
    implementation(libs.coroutines)
    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}