plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.android.asset-pack")
    kotlin("kapt")

}
assetPack {
    packName.set("assets") // Directory name for the asset pack
    dynamicDelivery {
        deliveryType.set("[ install-time | fast-follow | on-demand ]")
    }
}

android {
    namespace = "com.example.weatherapplication"
    compileSdk = 34
    assetPacks += listOf(":assets")
    defaultConfig {
        applicationId = "com.example.weatherapplication"
        minSdk = 31
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

}



dependencies {
    val fragment_version = "1.6.1"
    implementation("com.google.android.play:asset-delivery:2.1.0")
// For Kotlin use core-ktx
    implementation("com.google.android.play:asset-delivery-ktx:2.1.0")
    implementation("com.google.android.play:asset-delivery:2.1.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.android.play:asset-delivery-ktx:2.1.0")
    implementation ("androidx.activity:activity-ktx:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.dagger:hilt-android:2.48.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation ("androidx.navigation:navigation-dynamic-features-fragment:2.7.5")
    annotationProcessor("com.google.dagger:hilt-compiler:2.48.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("com.google.android.material:material:1.11.0-alpha03")
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // REST API calling library
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // JSON parsing library
    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")

}
