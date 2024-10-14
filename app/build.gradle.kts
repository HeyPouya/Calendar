plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltGradle)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.baselineprofile)
}

android {
    compileSdk = libs.versions.compileSdkVersion.get().toInt()
    defaultConfig {
        applicationId = "com.pouyaheydari.calendar"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = libs.versions.appVersion.get().toInt()
        versionName = libs.versions.appVersion.get().toCharArray().joinToString(".")
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    namespace = "com.pouyaheydari.calendar"
}

dependencies {
    implementation(projects.features.widget)
    implementation(projects.core)
    implementation(libs.androidx.core)

    // Baseline Profile
    implementation(libs.androidx.profileinstaller)
    "baselineProfile"(projects.baselineprofile)

    // Datetime
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.kotlinx.datetime)

    // Support
    implementation(libs.material)

    // Hilt Library
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)

    // compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.viewModel)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.compose.activity)

    // Firebase
    val firebaseBom = platform(libs.firebase.bom)
    implementation(firebaseBom)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    // testing and preview
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    testImplementation(libs.androidx.compose.ui.test.junit)
}
