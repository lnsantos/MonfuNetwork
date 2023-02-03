/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var quantityTryFind = 3
var dirs = "../"
var directoryDefault = "${dirs}monfu.properties"

val properties = java.util.Properties().also { prop ->
    while (quantityTryFind > 0) {
        file(directoryDefault).takeIf { it.exists() }?.run {
            println("> Task:Monfu: search file monfu.prop in round $quantityTryFind")
            quantityTryFind = -1
            prop.load(inputStream())
        }

        quantityTryFind--
        dirs += dirs
    }
}

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files(properties.getProperty("monfu.catalog.directory")))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")