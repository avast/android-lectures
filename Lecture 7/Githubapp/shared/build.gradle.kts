import co.touchlab.skie.configuration.SealedInterop

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    id("co.touchlab.skie")
    kotlin("plugin.serialization") version(libs.versions.kotlin)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                //put your multiplatform dependencies here
                implementation(project.dependencies.platform(libs.ktor.bom))
                implementation(libs.kotlinx.serialization.json)

                implementation(libs.ktor.client.contentnegotiation)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.resources)
                implementation(libs.ktor.serialization.json)

            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
//            debugImplementation(libs.okhttp.logging)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }
}

skie {
    features {
        group {
            SealedInterop.Enabled(true)
        }
    }
    analytics {
        disableUpload.set(true)
    }
}

android {
    namespace = "com.gendigital.mff.lecture7"
    compileSdk = 34
    defaultConfig {
        minSdk = 29
    }
}
