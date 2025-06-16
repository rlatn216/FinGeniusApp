plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
}

android {
    namespace = "com.skim.core.network"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.compatibility.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.compatibility.get().toInt())
    }

    kotlinOptions {
        jvmTarget = libs.versions.kotlin.jvm.target.get()
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")

    // retrofit2
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp3.logging)

    implementation(libs.kotlinx.serialization.json)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:datastore"))
}