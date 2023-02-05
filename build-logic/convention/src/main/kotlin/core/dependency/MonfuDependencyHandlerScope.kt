package core.dependency

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

open class MonfuDependencyHandlerScope(
    override val handlerScope: DependencyHandlerScope,
    private val versionCatalog: VersionCatalog
) : MonfuDependencyDelegate(),
    MonfuCatalogDependencyHandler {

    private fun findCatalogLibrary(library: String): Any {
        return versionCatalog.findLibrary(library).get().also {
            monfuLog("library find is ${it.get()}")
        }
    }
    override fun catalogImplementation(reference: String) { implementation(findCatalogLibrary(reference)) }

    override fun catalogTestImplementation(reference: String) { testImplementation(findCatalogLibrary(reference)) }

    override fun catalogKapt(reference: String) { kapt(findCatalogLibrary(reference) ) }

    override fun catalogKaptAndroidTest(reference: String) { kaptAndroidTest(findCatalogLibrary(reference)) }
    override fun catalogAndroidTestImplementation(reference: String) {
        androidTestImplementation(findCatalogLibrary(reference))
    }
}
