package core.dependency

import core.MonfuContext

interface MonfuDependencyHandler<T> : MonfuContext {
    fun applyLibrary(library: T) : Any
}