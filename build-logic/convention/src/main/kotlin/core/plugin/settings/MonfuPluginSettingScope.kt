package core.plugin.settings

import com.android.build.gradle.LibraryExtension

class MonfuPluginSettingScope(
    private val context: LibraryExtension
) : MonfuPluginSettingDelegate() {

    override val common = context

}
