plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
android {
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    namespace = "ir.apptune.calendar.details"
}

dependencies {
    implementation(project(":core"))
    implementation(libs.androidx.core)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.lifecycle.liveData)
    implementation(libs.lifecycle.viewModel)

    // Hilt Library
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)

}
