package internal

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import top.softnepo.public.easyLogicBuild

class InternalBasicBuildConfigConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) = target.easyLogicBuild {
        this@easyLogicBuild.compileSdk = 32
        this@easyLogicBuild.targetSdk = 32
        this@easyLogicBuild.onDefaultConfig {
            this@onDefaultConfig.minSdk = 21
            this@onDefaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            this@onDefaultConfig.setProguardFiles(listOf("consumer-rules.pro"))
        }
        this@easyLogicBuild.onCompileOptions {
            this@onCompileOptions.sourceCompatibility = JavaVersion.VERSION_1_8
            this@onCompileOptions.targetCompatibility = JavaVersion.VERSION_1_8
        }
        this@easyLogicBuild.onKotlinOptions { jvmTarget = "1.8" }
    }
}