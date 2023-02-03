package core.plugin

interface MonfuPluginManager<T> {
    fun applyPlugin(plugin: T)
}