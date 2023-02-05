package core

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.plugins.ExtensionAware

interface MonfuContext {

    companion object {
        const val REFERENCE_KOTLIN_OPTIONS : String = "kotlinOptions"
    }

    fun <T> T.monfuLog(description: String) {
        println("> ${this!!::class.simpleName}: $description")
    }

    fun <T> LibraryExtension.getExtensionAwareScope(
        name: String,
        action: T.() -> Unit
    ) = (this as ExtensionAware).extensions.configure(name, action)

    fun LibraryExtension.getAndroid() = this as CommonExtension<*, *, *, *>

}