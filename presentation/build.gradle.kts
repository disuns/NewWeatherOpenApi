plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.android.sj.presentation"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        missingDimensionStrategy("version", "weather")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += listOf("arch")

    productFlavors {
        create("mvvm") {
            dimension = "arch"
            buildConfigField("boolean", "USE_MVI", "false")
        }
        create("mvi") {
            dimension = "arch"
            buildConfigField("boolean", "USE_MVI", "true")
        }
    }

    sourceSets {
        getByName("mvvm") {
            java.srcDir("src/mvvm/java")
        }
        getByName("mvi") {
            java.srcDir("src/mvi/java")
        }
        getByName("main") {
            java.srcDir("src/main/java")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    androidTestImplementation(platform(libs.androidx.compose.bom))

    implementation(project(":domain"))
    implementation(project(":common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(libs.bundles.androidx.ui.test)
    debugImplementation(libs.bundles.debug.test)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.androidx.ui)
    implementation(libs.androidx.activity.compose)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.bundles.naver.map)

    implementation(libs.glide)

    implementation(libs.accompanist.permissions)
    implementation(kotlin("reflect"))

    implementation(libs.bundles.androidx.core.lifecycle)
}