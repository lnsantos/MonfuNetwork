package core.dependency

interface MonfuCatalogDependencyHandler {

    fun catalogImplementation(reference: String)
    fun catalogTestImplementation(reference: String)
    fun catalogKapt(reference: String)
    fun catalogKaptAndroidTest(reference: String)
    fun catalogAndroidTestImplementation(reference: String)
}