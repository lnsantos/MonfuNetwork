package internal


import org.gradle.api.Plugin
import org.gradle.api.Project
import top.softnepo.public.easyLogicDependency
import top.softnepo.public.easyLogicPlugins

class InternalLibrarySettingsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) : Unit = with(target) {
        easyLogicPlugins {
            applyPlugin("com.android.library")
            applyPlugin("org.jetbrains.kotlin.android")
            applyPlugin("monfu.internal.default.build")
        }

        easyLogicDependency {
            catalogImplementation("core.kotlin")
            catalogImplementation("core.android.coroutines")
            catalogImplementation("core.retrofit.library")
            catalogImplementation("core.retrofit.gson")

            catalogTestImplementation("unit.test.junit")
        }
    }
}