package core

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import core.dependency.MonfuDependencyHandlerScope
import core.plugin.MonfuPluginManagerScope
import core.plugin.settings.MonfuPluginSettingScope
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.configure
fun Project.getLibrary(
    onLibraryExtension : LibraryExtension.() -> Unit
) = extensions.configure<LibraryExtension> {
    onLibraryExtension(this)
}

fun Project.getAndroid() = this as CommonExtension<*, *, *, *>

internal fun Project.getApplication() = run {
    var instance: ApplicationExtension? = null
    extensions.configure<ApplicationExtension> {
        instance = this
    }
    instance!!
}
internal fun Project.getCatalog() = extensions.getByType<VersionCatalogsExtension>().named("libs")
internal fun Project.monfuDependencies(configuration: MonfuDependencyHandlerScope.() -> Unit) {
    dependencies { configuration(MonfuDependencyHandlerScope(this, getCatalog())) }
}

internal fun Project.monfuPlugins(onPlugin: MonfuPluginManagerScope.() -> Unit) {
    onPlugin(MonfuPluginManagerScope(pluginManager))
}
internal fun Project.monfuSettings(scope: MonfuPluginSettingScope.() -> Unit) {
    getLibrary { scope(MonfuPluginSettingScope(this)) }
}