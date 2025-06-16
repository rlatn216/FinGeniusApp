plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin)
//    alias(libs.plugins.com.google.firebase.crashlytics)
//    alias(libs.plugins.com.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.skim.fingeniusapp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.skim.fingeniusapp"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = 34
        versionCode = libs.versions.targetSdk.get().toInt()
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    applicationVariants.all {
        val server = productFlavors[0].name
        val buildType = buildType.name
        val version = versionName

        outputs.map {
            it as com.android.build.gradle.internal.api.BaseVariantOutputImpl
        }.forEach { output ->
            val outputFileName = "demoODS_${server}_${buildType}_v${version}.apk"
            output.outputFileName = outputFileName
        }
    }

    // key store custom 설정
    // key store 이거 있어야 같은 key store 앱 겹치면 업뎃
    signingConfigs {
        getByName("debug") {
            storeFile = file("key-store.jks")
            storePassword = "ss9571"
            keyAlias = "skim"
            keyPassword = "ss9571"
        }

        create("release") {
            storeFile = file("key-store.jks")
            storePassword = "ss9571"
            keyAlias = "skim"
            keyPassword = "ss9571"
        }
    }


    buildTypes {
        debug {
            isMinifyEnabled = false // 코드 난독화
            signingConfig = signingConfigs.getByName("debug") // key store 설정참조해서 서명
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            ) // 난독화에 필요한 파일 설정
        }
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.version.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.compatibility.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.compatibility.get().toInt())
    }
    kotlinOptions {
        jvmTarget = libs.versions.kotlin.jvm.target.get()
    }
    viewBinding {
        enable = true
    }
    dataBinding {
        enable = true
    }

    flavorDimensions += listOf("SERVER")

    // 여기서 대부분 개발, 운영 서버 주소를 나눈다
    // 예) buildConfigField("String", "API_SERVER_URL", "\"61.111.111.11:1111/\"")
    productFlavors {
        // 개발
        create("dev") {
            dimension = "SERVER"
            applicationIdSuffix = ".dev"
            buildConfigField("Boolean", "TEST_BUTTON", "true")
        }
        // 내부 개발서버
        create("itest") {
            dimension = "SERVER"
            applicationIdSuffix = ".itest"
            buildConfigField("Boolean", "TEST_BUTTON", "false")
        }
        // 클라우드 운영서버
        create("real") {
            dimension = "SERVER"
            buildConfigField("Boolean", "TEST_BUTTON", "false")
        }
    }

}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Compose
    implementation(platform(libs.compose.bom))
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.viewbinding)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.activity.compose)

    implementation(libs.lifecycle.viewmodel.compose)

    // Kotlin Navigation
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.fragment.ktx)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Firebase
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.analytics.ktx)
//    implementation(libs.firebase.crashlytics.ktx)

    implementation(libs.kotlinx.serialization.json)
}