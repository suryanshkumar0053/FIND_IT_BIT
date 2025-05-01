plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-parcelize")
    }



android {
    namespace = "com.example.find_it_bit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.find_it_bit"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Core AndroidX & ViewModel stuff
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")


    // ---------- Compose Material & UI ----------
    implementation("androidx.compose.material:material:1.8.0") // Latest Material Components (Buttons, Cards, etc.)
    implementation("androidx.compose.material:material-icons-extended:1.7.8") // Extended icons for Material
    implementation("androidx.compose.ui:ui:1.5.4") // Core Jetpack Compose UI toolkit
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4") // Enables previewing Composables in Android Studio
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4") // Debug tools for Compose UI

// ---------- Coil (Image Loading in Compose) ----------
    implementation("io.coil-kt:coil-compose:2.4.0") // Efficient image loading library for Compose

// ---------- Firebase (BOM manages versions) ----------
    implementation(platform("com.google.firebase:firebase-bom:33.12.0")) // Bill of Materials to manage Firebase versions

// Firebase Core Services
    implementation("com.google.firebase:firebase-auth-ktx") // Firebase Authentication (Email/Phone auth)
    implementation("com.google.firebase:firebase-firestore-ktx") // Cloud Firestore for real-time database
    implementation("com.google.firebase:firebase-storage-ktx") // Firebase Storage for image/file uploads

// ---------- Firebase Extras ----------
    implementation("com.google.android.gms:play-services-auth:20.7.0") // Required for Google Sign-In / Phone Auth

// ---------- Jetpack Navigation ----------
    implementation("androidx.navigation:navigation-compose:2.7.6") // Navigation component for Compose

// ---------- Lifecycle ----------
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2") // ViewModel support in Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2") // Observe lifecycle state in Compose

// ---------- Compose Activity ----------
    implementation("androidx.activity:activity-compose:1.8.2") // Integration between Compose and Activity lifecycle

// ---------- Accompanist (Compose utilities) ----------
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.1-alpha") // Control system UI (status/nav bar colors)
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha") // Handle runtime permissions in Compose





    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}