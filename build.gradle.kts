// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.baselineprofile) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0" apply false
}

tasks.register("generateAndBuildWithBaselineProfile") {
    group = "build"
    description = "Generates the Baseline Profile and builds the release APK."

    dependsOn(":baselineprofile:connectedBenchmark")

    doLast {
        val sourceFile = file("baselineprofile/build/outputs/baseline-prof/profile-baseline-prof.txt")
        val destFile = file("app/src/main/baseline-prof.txt")

        if (!sourceFile.exists()) {
            throw GradleException("❌ Baseline profile not found at ${sourceFile.path}.")
        }

        println("📦 Copying baseline profile to ${destFile.path}")
        sourceFile.copyTo(destFile, overwrite = true)
        println("✅ Baseline profile copied successfully.")
    }

    finalizedBy(":app:assembleRelease")
}
