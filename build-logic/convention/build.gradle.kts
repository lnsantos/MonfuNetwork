plugins { `kotlin-dsl` }

group = "com.lnsantos.monfunetwork.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.gradle.android.plugin)
    compileOnly(libs.gradle.kotlin.plugin)
    compileOnly(gradleApi())
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