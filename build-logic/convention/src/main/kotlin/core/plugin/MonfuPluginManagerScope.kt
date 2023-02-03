package core.plugin

import org.gradle.api.plugins.PluginManager

internal class MonfuPluginManagerScope(
    private val pluginManager: PluginManager
) : MonfuPluginManager<String> {

    override fun applyPlugin(plugin: String) = pluginManager.apply(plugin)

}