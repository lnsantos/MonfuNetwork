package core.dependency

import core.MonfuContext.Companion.DEPENDENCY_ANDROID_TEST_IMPLEMENTATION
import core.MonfuContext.Companion.DEPENDENCY_IMPLEMENTATION
import core.MonfuContext.Companion.DEPENDENCY_KAPT
import core.MonfuContext.Companion.DEPENDENCY_KAPT_ANDROID_TEST
import core.MonfuContext.Companion.DEPENDENCY_TEST_IMPLEMENTATION
import org.gradle.kotlin.dsl.DependencyHandlerScope

abstract class MonfuDependencyDelegate : MonfuDependencyHandler<Any> {

    abstract val handlerScope: DependencyHandlerScope

    override fun implementation(reference: Any) {
        handlerScope.add(DEPENDENCY_IMPLEMENTATION, reference)
    }

    override fun testImplementation(reference: Any) {
        handlerScope.add(DEPENDENCY_TEST_IMPLEMENTATION, reference)
    }

    override fun kapt(reference: Any) {
        handlerScope.add(DEPENDENCY_KAPT, reference)
    }

    override fun kaptAndroidTest(reference: Any) {
        handlerScope.add(DEPENDENCY_KAPT_ANDROID_TEST, reference)
    }

    override fun androidTestImplementation(reference: Any) {
        handlerScope.add(DEPENDENCY_ANDROID_TEST_IMPLEMENTATION, reference)
    }
}

