plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.com.google.dagger.hilt.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.skim.core.datastore"
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

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Preferences DataStore
    implementation(libs.datastore.preferences)
    implementation(libs.protobuf.kotlin.lite)


//    implementation(project(":core:model"))
    implementation(project(":core:common"))
}