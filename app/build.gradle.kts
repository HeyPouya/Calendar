plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":features:widget"))
    implementation(project(":core"))
    implementation(project(":features:calendar-details"))
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
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)

}
kapt {
    correctErrorTypes = true
}
