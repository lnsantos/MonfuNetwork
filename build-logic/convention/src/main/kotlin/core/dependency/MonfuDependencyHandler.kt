package core.dependency

interface MonfuDependencyHandler<T> {
    fun applyLibrary(library: T) : Any
}