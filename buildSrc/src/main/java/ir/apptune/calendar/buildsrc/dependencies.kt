package ir.apptune.calendar.buildsrc


object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.2"

    object Kotlin {
        private const val version = "1.5.30"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"

        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.5.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Navigation {
        private const val version = "2.3.5"
        const val navigation = "androidx.navigation:navigation-fragment-ktx:$version"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object Hilt {
        private const val version = "2.38.1"
        private const val hilt_viewModel_version = "1.0.0"
        const val android = "com.google.dagger:hilt-android:$version"
        const val androidCompiler = "com.google.dagger:hilt-android-compiler:$version"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val compiler = "androidx.hilt:hilt-compiler:$hilt_viewModel_version"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.6.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val appcompat = "androidx.appcompat:appcompat:1.3.1"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.6"
    }

    object Material {
        const val android = "com.google.android.material:material:1.4.0"
    }

    object Lifecycle {
        const val version = "2.4.0-rc01"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    }
}