import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.getkeepsafe.dexcount")
    kotlin("plugin.serialization")
    kotlin("kapt")
}

// Create a variable called keystorePropertiesFile, and initialize it to your
// keystore.properties file, in the rootProject folder.
val keystorePropertiesFile = rootProject.file("keystore.properties")

// Initialize a new Properties() object called keystoreProperties.
val keystoreProperties = Properties()

// Load your keystore.properties file into the keystoreProperties object.
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.avast.mff.lecture6"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.avast.mff.lecture6"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String?
            keyPassword = keystoreProperties["keyPassword"] as String?
            storeFile = keystoreProperties["storeFile"]?.let { file(it) }
            storePassword = keystoreProperties["storePassword"] as String?
        }
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs["release"]

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.appcompat:appcompat:1.5.1")
    implementation ("com.google.android.material:material:1.7.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.fragment:fragment-ktx:1.5.5")
    implementation ("androidx.annotation:annotation:1.5.0")
    implementation ("androidx.cardview:cardview:1.0.0")

    implementation ("io.ktor:ktor-bom:2.1.3")
    implementation ("io.ktor:ktor-client-content-negotiation")
    implementation ("io.ktor:ktor-client-core")
    implementation ("io.ktor:ktor-client-logging")
    implementation ("io.ktor:ktor-client-okhttp")
    implementation ("io.ktor:ktor-serialization-kotlinx-json")

    implementation ("org.slf4j:slf4j-android:1.7.36")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    debugImplementation ("com.facebook.flipper:flipper:0.174.0")
    debugImplementation ("com.facebook.flipper:flipper-network-plugin:0.174.0")
    debugImplementation ("com.facebook.soloader:soloader:0.10.4")

    releaseImplementation ("com.facebook.flipper:flipper-noop:0.174.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android")

    implementation ("com.github.bumptech.glide:glide:4.14.2")
    kapt ("com.github.bumptech.glide:compiler:4.14.2")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.4")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.0")
}