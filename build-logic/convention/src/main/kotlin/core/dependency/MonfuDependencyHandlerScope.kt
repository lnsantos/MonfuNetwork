package core.dependency

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope


open class MonfuDependencyHandlerScope(
    private val handlerScope: DependencyHandlerScope,
    private val extensions: VersionCatalog
) : MonfuDependencyDelegate() {
    override val versionCatalog: VersionCatalog = extensions

    fun catalogImplementation(reference: String) {
        handlerScope.add("implementation", applyLibrary(reference))
    }

    fun catalogTestImplementation(reference: String) {
        handlerScope.add("testImplementation", applyLibrary(reference))
    }
}
