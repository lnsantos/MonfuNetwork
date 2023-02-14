package library

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.plugins.signing.SigningExtension
import top.softnepo.public.easyLogicPlugins
import java.util.*

class LibraryPublishingMonfuNetwork : Plugin<Project> {

    override fun apply(target: Project) {
        target.easyLogicPlugins {
            applyPlugin("maven-publish")
            applyPlugin("signing")
        }

        val properties = Properties()
        val gradleDirectory = target.gradle.gradleUserHomeDir.path
        properties.load(target.file("$gradleDirectory/gradle.properties").inputStream())

        target.publishing {

            publications {
                create<MavenPublication>("libraryMavenNetwork") {
                    groupId = "io.github.lnsantos"
                    artifactId = "monfu-network"
                    version = "0.0.3-alpha"

                    val directory = "${target.buildDir.path}/outputs/aar/${target.project.name}-release.aar"
                    artifact(directory.also { println("directory find is $it") })

                    pom {
                        name.set("Monfu Network")
                        description.set("Open source library to help android projects with an internate connection wrapper")
                        url.set("https://github.com/lnsantos/MonfuNetwork")
                        inceptionYear.set("2023")

                        licenses {
                            license {
                                name.set("MIT License")
                                url.set("https://opensource.org/licenses/MIT")
                            }
                        }

                        developers {
                            developer {
                                id.set(System.getenv("abd") ?: properties.getProperty("publish_sonatype_login"))
                                name.set(System.getenv("publish_sonatype_name") ?: properties.getProperty("publish_sonatype_name"))
                                email.set(System.getenv("publish_sonatype_email") ?: properties.getProperty("publish_sonatype_email"))
                            }
                        }
                        scm {
                            connection.set("scm:git:git:github.com/lnsantos/MonfuNetwork.git")
                            developerConnection.set("cm:git:ssh://github.com/lnsantos/MonfuNetwork.git")
                            url.set("https://github.com/lnsantos/MonfuNetwork")
                        }
                    }
                }

                repositories {
                    maven {
                        name = "OSSRH"
                        setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                        credentials {
                            val username: String = System.getenv("publish_sonatype_login") ?: properties.getProperty("publish_sonatype_login")
                            val pass: String = System.getenv("publish_sonatype_pass") ?: properties.getProperty("publish_sonatype_pass")
                            setUsername(username)
                            setPassword(pass)
                        }
                    }
                }
            }
        }

        target.configure<SigningExtension> {
            isRequired = true
            val publishing: PublishingExtension by project
            useGpgCmd()
            sign(publishing.publications)
        }
    }


    private fun <T> Project.getExtensions(name: String, context: Action<T>) {
        (this as ExtensionAware).extensions.configure(name, context)
    }

    private fun Project.publishing(configure: Action<PublishingExtension>): Unit = getExtensions("publishing", configure)
}