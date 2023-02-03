package internal

import core.monfuDependencies
import core.monfuPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

class InternalLibrarySettingsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) : Unit = with(target) {
        monfuPlugins {
            applyPlugin("com.android.library")
            applyPlugin("org.jetbrains.kotlin.android")
        }

        monfuDependencies {
            catalogImplementation("core.retrofit.library")
            catalogImplementation("core.retrofit.gson")
        }
    }
}
