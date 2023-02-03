package core

import core.dependency.MonfuDependencyHandlerScope
import core.plugin.MonfuPluginManagerScope
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.monfuDependencies(configuration: MonfuDependencyHandlerScope.() -> Unit) {
    val catalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies { configuration(MonfuDependencyHandlerScope(this, catalog)) }
}

internal fun Project.monfuPlugins(onPlugin: MonfuPluginManagerScope.() -> Unit) {
    onPlugin(MonfuPluginManagerScope(pluginManager))
}
