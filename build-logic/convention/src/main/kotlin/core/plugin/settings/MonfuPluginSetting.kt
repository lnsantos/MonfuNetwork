package core.plugin.settings

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.CompileOptions
import com.android.build.api.dsl.DefaultConfig
import com.android.build.gradle.LibraryExtension
import core.MonfuContext
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

typealias AndroidDefaultConfig = com.android.build.gradle.internal.dsl.DefaultConfig

interface MonfuPluginSetting : MonfuContext {
    var compileSdk: Int?
    var packagename: String?
    fun setNamespace(reference: String)
    fun setCompileSDK(version: Int)
    fun setTargetSdk(version: Int)

    fun onAndroidDefaultConfig(config: AndroidDefaultConfig.() -> Unit)
    fun onDefaultConfig(config: DefaultConfig.() -> Unit)
    fun onCompileOptions(options: CompileOptions.() -> Unit)
    fun onKotlinOptions(options: KotlinJvmOptions.() -> Unit)
}