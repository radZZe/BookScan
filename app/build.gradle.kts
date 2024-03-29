plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "ru.radzze.bookscan"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.radzze.bookscan"
        minSdk = 27
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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    hilt {
        enableAggregatingTask = false
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    onboardingFeatureApi()
    onboardingFeatureImpl()
    core()
    authFeatureImpl()
    authFeatureApi()
    //implementation(project(":data"))

    hilt()
    compose()
    navigation()

    implementation(Dependencies.core_ktx)
    implementation(Dependencies.lifecycle_runtime_ktx)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.ext_junit)
    androidTestImplementation(Dependencies.espresso)
    androidTestImplementation(platform(Dependencies.compose_bom))
    androidTestImplementation(Dependencies.compose_junit)
}