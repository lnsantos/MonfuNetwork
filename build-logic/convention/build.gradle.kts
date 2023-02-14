import java.net.URI
plugins { `kotlin-dsl` }

group = "com.lnsantos.monfunetwork.buildlogic"
version = "0.0.2"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    google()
    mavenCentral()
    maven { url = URI("https://s01.oss.sonatype.org/content/repositories/topsoftnepo-1005") }
}

dependencies {
    compileOnly(libs.gradle.android.plugin)
    compileOnly(libs.gradle.kotlin.plugin)
    compileOnly(gradleApi())
    implementation("top.softnepo:easy-logic:0.0.12-experimental")
}

gradlePlugin {
    plugins {
        register("internalLibrarySettingsConventionPlugin"){
            id = "monfu.internal.default.library"
            implementationClass = "internal.InternalLibrarySettingsConventionPlugin"
        }
        register("internalBasicBuildConfigConventionPlugin") {
            id = "monfu.internal.default.build"
            implementationClass = "internal.InternalBasicBuildConfigConventionPlugin"
        }
        register("libraryPublishingMonfuNetwork") {
            id = "monfu.publish.monfunetwork"
            implementationClass = "library.LibraryPublishingMonfuNetwork"
        }
    }
}