plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id("kotlin-kapt")
}

android {
    compileSdk = Android.compileSdk
    @Suppress("UnstableApiUsage")
    buildToolsVersion = Android.buildTools
    namespace = Android.appId

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "com.codingwithmitch.dotainfo.CustomTestRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.kotlinCompilerComposeVersion
    }
    packaging {
        resources {
            excludes += setOf("META-INF/AL2.0", "META-INF/LGPL2.1")
        }
    }
}

dependencies {

    implementation(project(Modules.core))

    implementation(project(Modules.moviesListData))
    implementation(project(Modules.moviesListDomain))
    implementation(project(Modules.moviesListPresentation))


    implementation(project(Modules.moviesDetailsData))
    implementation(project(Modules.moviesDetailsDomain))
    implementation(project(Modules.moviesDetailsPresentation))

    implementation(Coil.coil)

    implementation(Accompanist.animations)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleVmKtx)

    implementation(Compose.activity)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.tooling)
    implementation(Compose.navigation)
    implementation(Compose.hiltNavigation)

    implementation(Google.material)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    implementation(Kotlinx.serialization)
    implementation(Ktor.core)

    androidTestImplementation(AndroidXTest.runner)
    androidTestImplementation(ComposeTest.uiTestJunit4)
    debugImplementation(ComposeTest.uiTestManifest)
    androidTestImplementation(HiltTest.hiltAndroidTesting)
    kaptAndroidTest(Hilt.compiler)
    androidTestImplementation(Junit.junit4)
}