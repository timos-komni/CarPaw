import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin(libs.plugins.kotlin.serialization.get().pluginId).version(libs.versions.kotlin)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")

val keystoreProperties = Properties()

keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.timkom.carpaw"

    signingConfigs {
        /*create("debug") {
            keyAlias = keystoreProperties["debugKeyAlias"].toString()
            keyPassword = keystoreProperties["debugKeyPassword"].toString()
            storeFile = file(keystoreProperties["debugKeyPassword"].toString())
            storePassword = keystoreProperties["debugStorePassword"].toString()
        }*/
        create("debug2") {
            keyAlias = keystoreProperties["debugKeyAlias"].toString()
            keyPassword = keystoreProperties["debugKeyPassword"].toString()
            storeFile = file(keystoreProperties["debugKeyPassword"].toString())
            storePassword = keystoreProperties["debugStorePassword"].toString()
        }
        create("release") {
            keyAlias = keystoreProperties["releaseKeyAlias"].toString()
            keyPassword = keystoreProperties["releaseKeyPassword"].toString()
            storeFile = file(keystoreProperties["releaseKeyPassword"].toString())
            storePassword = keystoreProperties["releaseStorePassword"].toString()
        }
    }

    compileSdk = 34

    defaultConfig {
        applicationId = "com.timkom.carpaw"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            //signingConfig = signingConfigs["release"]
        }
        debug {
           // signingConfig = signingConfigs["debug2"]
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.io.supabase.bom))
    implementation(libs.io.supabase.postgrest.kt)
    implementation(libs.io.supabase.storage.kt)
    implementation(libs.io.ktor.client.android)
    implementation(libs.google.accompanist.pager.indicators)
    implementation(platform(libs.kotlin.bom))
    implementation(libs.sheets.compose.dialogs.core)
    implementation(libs.sheets.compose.dialogs.calendar)
    implementation(libs.sheets.compose.dialogs.clock)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.google.android.libraries.places)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

secrets {
    propertiesFileName = "secrets.properties"

    defaultPropertiesFileName = "secrets.properties.example"

    ignoreList.add("sdk.*")
}
