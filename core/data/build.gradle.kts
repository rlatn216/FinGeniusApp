plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
}

android {
    namespace = "com.skim.core.data"
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

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

//    implementation(project(":core:model"))
    implementation(project(":core:common"))
//    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
}