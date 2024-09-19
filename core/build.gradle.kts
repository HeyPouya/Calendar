plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
}

android {
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.pouyaheydari.calendar.core"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.kotlinx.datetime)
    implementation(libs.javax.inject)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)
}
