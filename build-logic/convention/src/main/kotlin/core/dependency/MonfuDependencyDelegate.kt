package core.dependency

import org.gradle.api.artifacts.VersionCatalog

abstract class MonfuDependencyDelegate : MonfuDependencyHandler<String> {

    abstract val versionCatalog: VersionCatalog

    override fun applyLibrary(library: String) : Any {
        return versionCatalog.findLibrary(library).get().also {
            monfuLog("library find is ${it.get()}")
        }
    }
}
