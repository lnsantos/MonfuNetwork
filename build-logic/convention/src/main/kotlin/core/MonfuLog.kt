package core

internal fun <T> T.monfuLog(description: String) {
    println("> ${this!!::class.simpleName}: $description")
}

