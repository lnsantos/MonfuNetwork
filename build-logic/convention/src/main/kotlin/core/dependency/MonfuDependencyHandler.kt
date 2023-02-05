package core.dependency

import core.MonfuContext

interface MonfuDependencyHandler<T> : MonfuContext {
    fun implementation(reference: T)
    fun testImplementation(reference: T)
    fun kapt(reference: T)
    fun kaptAndroidTest(reference: T)
    fun androidTestImplementation(reference: T)
}