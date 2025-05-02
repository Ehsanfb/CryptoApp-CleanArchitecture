plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"

    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")

    kotlin("kapt")
}

android {
    namespace = "com.example.cryptoapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cryptoapp"
        minSdk = 24
        targetSdk = 34
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

val composeBom = "androidx.compose:compose-bom:2024.04.01"
val lifecycleVersion = "2.8.4"
val coroutinesVersion = "1.6.0"
val retrofitVersion = "2.11.0"
val okhttpVersion = "4.10.0"
val gsonVersion = "2.11.0"
val rxjavaVersion = "3.1.5"
val rxandroidVersion = "3.0.0"
val hiltVersion = "2.51.1"

dependencies {
    // Lifecycle
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    // Compose BOM
    implementation(platform(composeBom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.navigation:navigation-compose:2.7.1")
    implementation("androidx.activity:activity-compose:1.10.1")

    // AndroidX
    implementation("androidx.appcompat:appcompat:1.7.0")

    // RxJava
    implementation("io.reactivex.rxjava3:rxjava:$rxjavaVersion")
    implementation("io.reactivex.rxjava3:rxandroid:$rxandroidVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // Retrofit / Gson / OkHttp
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

    // Hilt
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}




