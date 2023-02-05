package core.plugin.settings

import com.android.build.api.dsl.CompileOptions
import com.android.build.api.dsl.DefaultConfig
import com.android.build.gradle.internal.dsl.DefaultConfig as InternalDefaultConfig
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.AndroidSourceSet
import com.android.build.gradle.api.LibraryVariant
import com.android.build.gradle.internal.dsl.BuildType
import com.android.build.gradle.internal.dsl.ProductFlavor
import core.MonfuContext
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.internal.DefaultDomainObjectSet

abstract class MonfuPluginSettingDelegate : MonfuPluginSetting {

    abstract val common: LibraryExtension

    override var compileSdk: Int?
        get() = common.compileSdk
        set(value) {
            value?.also { setCompileSDK(it) }
        }

    override var packagename: String?
        get() = common.namespace
        set(value) {
            value?.also { setNamespace(it) }
        }

    override val libraryVariants: DefaultDomainObjectSet<LibraryVariant>
        get() = common.libraryVariants

    override val buildTypes: NamedDomainObjectContainer<BuildType>
        get() = common.buildTypes

    override val defaultConfig: InternalDefaultConfig
        get() = common.defaultConfig

    override val productFlavors: NamedDomainObjectContainer<ProductFlavor>
        get() = common.productFlavors

    override val sourceSets: NamedDomainObjectContainer<AndroidSourceSet>
        get() = common.sourceSets

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
        common.getExtensionAwareScope(MonfuContext.REFERENCE_KOTLIN_OPTIONS, options)
    }

    override fun onLibraryExtension(context: LibraryExtension.() -> Unit) {
        context(common)
    }
}
