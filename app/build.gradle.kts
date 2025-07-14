import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
//    alias(libs.plugins.google.service)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.codedevs.newweatheropenapi"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.codedevs.newweatheropenapi"
        minSdk = 24
        targetSdk = 36
        versionName = "1.0.11"
        val versionParts = versionName.toString().split(".")
        versionCode = versionParts[0].toInt() * 10000 + versionParts[1].toInt() * 100 + versionParts[2].toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val keystoreProperties = Properties()

    if (keystorePropertiesFile.exists()) {
        FileInputStream(keystorePropertiesFile).use { fis ->
            keystoreProperties.load(fis)
        }
    }

    signingConfigs {
        create("release") {
            if (keystorePropertiesFile.exists()) {
                storeFile = file(keystoreProperties.getProperty("storeFile")!!)
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            } else {
                storeFile = file("release.keystore")
                storePassword = System.getenv("STORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }
    buildFeatures{
        buildConfig = true
    }

    flavorDimensions += listOf("version", "arch")
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["appNameSuffix"] = ""
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            applicationIdSuffix = ".debug"
            manifestPlaceholders["appNameSuffix"] = "[개발]"
        }
//        create("benchmark") {
//            initWith(buildTypes.getByName("release"))
//            matchingFallbacks += listOf("release")
//            isDebuggable = false
//        }
    }

    productFlavors {
        create("weather") {
            dimension = "version"
            manifestPlaceholders["appName"] = "날씨 및 미세먼지 확인"
        }
        create("mvvm") {
            dimension = "arch"
            buildConfigField("boolean", "USE_MVI", "false")
        }
        create("mvi") {
            dimension           = "arch"
            buildConfigField("boolean", "USE_MVI", "true")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(project(":data"))
    implementation(project(":presentation"))

    androidTestImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.bundles.testing)

    implementation(platform(libs.firebase.bom))

    ksp(libs.hilt.compiler)

    implementation(libs.gson)

    implementation(libs.bundles.hilt)
    implementation(libs.bundles.firebase)
    implementation(libs.bundles.networking)
    implementation(libs.google.location)
}