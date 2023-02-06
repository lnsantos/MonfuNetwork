package library

import core.monfuPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.plugins.signing.SigningExtension
import java.net.URI
import org.gradle.api.Action
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate

class LibraryPublishingMonfuNetwork : Plugin<Project> {

    override fun apply(target: Project) {
        target.monfuPlugins {
            applyPlugin("maven-publish")
            applyPlugin("signing")
        }

        target.publishing {
            publications {
                create<MavenPublication>("libraryMavenNetwork") {
                    groupId = "io.github.lnsantos"
                    artifactId = "monfu-network"
                    version = "0.0.1-alpha"
                    from(target.components.find { it.name == "java" })

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
                                id.set(System.getenv("publish_sonatype_id"))
                                name.set(System.getenv("publish_sonatype_name"))
                                email.set(System.getenv("publish_sonatype_email"))
                            }
                        }
                        scm {
                            connection.set("scm:git:git:github.com/lnsantos/MonfuNetwork.git")
                            developerConnection.set("cm:git:ssh://github.com/lnsantos/MonfuNetwork.git")
                            url.set("https://github.com/lnsantos/MonfuNetwork\"")
                        }
                    }
                }

                repositories {
                    maven {
                        name = "OSSRH"
                        setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                        credentials {
                            username = System.getenv("publish_sonatype_login")
                            password = System.getenv("publish_sonatype_pass")
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

        // target.signInConfigureMavenPublication("libraryMavenNetwork")
        /* target.signin {
            (target as org.gradle.api.plugins.ExtensionAware).extensions.configure<PublishingExtension> {
                sign(publications.getByName("libraryMavenNetwork"))
            }
        } */
        /*
        with(target as SigningExtension) {
            sign((target as PublishingExtension).publications.getByName("libraryMavenNetwork"))
        }*/
    }


    private fun <T> Project.getExtensions(name: String, context: Action<T>) {
        (this as ExtensionAware).extensions.configure(name, context)
    }

    private fun Project.publishing(configure: Action<PublishingExtension>): Unit = getExtensions("publishing", configure)
    private fun Project.signin(configure: Action<SigningExtension>): Unit = getExtensions("signing", configure)
    private fun Project.signInConfigureMavenPublication(reference: String) {
        publishing { signin { sign(publications.getByName(reference)) } }
    }
}