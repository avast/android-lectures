// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.ksp) apply false
    id("co.touchlab.skie") version "0.6.1" apply false

    id("com.getkeepsafe.dexcount") version "4.0.0"
}