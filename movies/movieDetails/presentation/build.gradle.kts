
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id("kotlin-kapt")
}
android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools
    namespace = "com.moviedetails.presentation"

    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.kotlinCompilerComposeVersion
        kotlinCompilerVersion = Kotlin.version
    }
    packaging {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(Modules.components))
    implementation(project(Modules.core))
    implementation(project(Modules.constants))

    implementation(project(Modules.movieDetailsDomain))
    implementation(project(Modules.movieDetailsInteractors))
    implementation(project(Modules.moviesListDomain))

    implementation(Coil.coil)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleVmKtx)

    implementation(Compose.activity)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.tooling)
    implementation(Compose.util)
    implementation(Compose.navigation)

    implementation(Google.material)
    implementation(Kotlinx.coroutinesCore)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    implementation(YoutubePlayer.player)
}
