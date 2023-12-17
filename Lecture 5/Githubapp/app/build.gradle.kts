import org.jetbrains.kotlin.tooling.core.linearClosure

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization").version("1.9.10")

    id("com.getkeepsafe.dexcount")
}

android {
    namespace = "com.gendigital.mff.lecture4"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gendigital.mff.lecture4"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    implementation(libs.core.ktx)
    implementation (libs.androidx.cardview)
    implementation (libs.androidx.fragment.ktx)

    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.client.contentnegotiation)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.resources)
    implementation(libs.ktor.serialization.json)
    implementation(libs.okhttp.logging.interceptor)

    implementation (libs.glide)
    ksp (libs.glide.compiler)

    debugImplementation(libs.flipper)
    debugImplementation(libs.soloader)
    debugImplementation(libs.flipper.network.plugin)
    releaseImplementation (libs.flipper.noop)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Data store
    implementation(libs.androidx.datastore)
    implementation(libs.kotlinx.serialization.json)
}