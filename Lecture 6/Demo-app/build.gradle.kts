import io.gitlab.arturbosch.detekt.extensions.DetektReports

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "7.3.1" apply false
    id ("com.android.library") version "7.3.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id ("com.getkeepsafe.dexcount") version "3.1.0" apply false
    id ("io.gitlab.arturbosch.detekt") version "1.22.0"
    kotlin("plugin.serialization") version "1.7.20" apply false
    kotlin("kapt") version "1.7.20" apply false
}

val detektReportMergeSarif by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.sarif"))
}

allprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        buildUponDefaultConfig = false
        config = files("$rootDir/config/detekt/detekt.yml")
        baseline = file("$projectDir/config/detekt/baseline.xml")
    }

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-rules-libraries:1.22.0")
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-rules-ruleauthors:1.22.0")
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt> detekt@{
        jvmTarget = "1.8"
        reports {
            // Enable/Disable XML report (default: true)
            xml.required.set(true)
            xml.outputLocation.set(file("$projectDir/build/reports/detekt.xml"))
            // Enable/Disable HTML report (default: true)
            html.required.set(true)
            html.outputLocation.set(file("$projectDir/build/reports/detekt.html"))
            // Enable/Disable TXT report (default: true)
            txt.required.set(true)
            txt.outputLocation.set(file("$projectDir/build/reports/detekt.txt"))
            // Enable/Disable SARIF report (default: false)
            sarif.required.set(true)
            sarif.outputLocation.set(file("$projectDir/build/reports/detekt.sarif"))
            // Enable/Disable MD report (default: false)
            md.required.set(true)
            md.outputLocation.set(file("$projectDir/build/reports/detekt.md"))
        }
        basePath = rootProject.projectDir.absolutePath
        finalizedBy(detektReportMergeSarif)
        detektReportMergeSarif.configure {
            input.from(this@detekt.sarifReportFile)
        }
    }
    tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
        jvmTarget = "1.8"
    }
}