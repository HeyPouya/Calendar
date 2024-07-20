plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltGradle)
    id("kotlin-parcelize")
}

android {
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        applicationId = "ir.apptune.calendar"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = libs.versions.appVersion.get().toInt()
        versionName = libs.versions.appVersion.get()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding = true
    }
    namespace = "ir.apptune.calendar"
}

dependencies {
    implementation(projects.features.widget)
    implementation(projects.core)
    implementation(projects.features.calendarDetails)
    // Kotlin
    implementation(libs.androidx.core)

    // Support
    implementation(libs.appcompat)
    implementation(libs.constraintLayout)
    implementation(libs.material)

    // ViewModel + LiveData
    implementation(libs.lifecycle.liveData)
    implementation(libs.lifecycle.viewModel)

    // Navigation Components
    implementation(libs.navigation.ui)
    implementation(libs.navigation)

    // Hilt Library
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)

}
