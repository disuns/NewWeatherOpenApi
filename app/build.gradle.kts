plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    kotlin("kapt")
//    alias(libs.plugins.google.service)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.project.newweatheropenapi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.newweatheropenapi"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        create("release") {
            storeFile = file("${project.rootDir}/WeatherKey.jks")
            storePassword = "weatherkeypassword"
            keyAlias = "weatherKey"
            keyPassword = "weatherkeypassword"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(libs.bundles.androidx.ui)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    androidTestImplementation(libs.bundles.androidx.ui.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.bundles.debug.test)
    testImplementation(libs.bundles.testing)

    implementation(libs.glide)

    implementation(platform(libs.firebase.bom))

    kapt(libs.hilt.compiler)

    implementation(libs.logger)

    implementation(libs.gson)

    implementation(libs.google.location)

    implementation(libs.accompanist.permissions)
    implementation(kotlin("reflect"))

    implementation(libs.bundles.androidx.core.lifecycle)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.naver.map)
    implementation(libs.bundles.firebase)
    implementation(libs.bundles.networking)
}