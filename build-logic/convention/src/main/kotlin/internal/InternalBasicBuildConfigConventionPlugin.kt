package internal

import core.monfuSettings
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class InternalBasicBuildConfigConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) = with(target) {
        monfuSettings {
            setCompileSDK(32)
            setTargetSdk(32)
            onDefaultConfig {
                minSdk = 21
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                setProguardFiles(listOf("consumer-rules.pro"))
            }
            onCompileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
            onKotlinOptions { jvmTarget = "1.8" }
        }
    }
}