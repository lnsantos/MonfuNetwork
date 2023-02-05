package core.plugin.settings

import com.android.build.api.dsl.CompileOptions
import com.android.build.api.dsl.DefaultConfig
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import com.android.build.gradle.LibraryExtension
import core.plugin.settings.MonfuPluginSetting.Companion.getExtensionAwareScope

abstract class MonfuPluginSettingDelegate : MonfuPluginSetting {

    abstract val common: LibraryExtension

    override fun setNamespace(reference: String) {
        common.namespace = reference
    }

    override fun setCompileSDK(version: Int) {
        common.takeIf { it.compileSdk != version }?.let { it.compileSdk = version }
    }

    override fun setTargetSdk(version: Int) {
        common.defaultConfig.targetSdk = version
    }

    override fun onDefaultConfig(config: DefaultConfig.() -> Unit) {
        config(common.defaultConfig)
    }

    override fun onCompileOptions(options: CompileOptions.() -> Unit) {
        options(common.compileOptions)
    }

    override fun onAndroidDefaultConfig(config: AndroidDefaultConfig.() -> Unit) {
       config(common.defaultConfig)
    }

    override fun onKotlinOptions(options: KotlinJvmOptions.() -> Unit) {
        common.getExtensionAwareScope("kotlinOptions", options)
    }
}
