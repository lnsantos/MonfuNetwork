package core.plugin.settings

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import core.getApplication
import core.plugin.settings.MonfuPluginSetting.Companion.getAndroid
import org.gradle.api.Project

class MonfuPluginSettingScope(
    private val context: LibraryExtension
) : MonfuPluginSettingDelegate() {

    override val common = context

}
