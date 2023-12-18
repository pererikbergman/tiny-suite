import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    id("maven-publish")
    id("signing")
}

group = "com.rakangsoftware.tiny"
version = "0.0.1"

kotlin {
    androidTarget {
        publishLibraryVariants("release")

        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            xcf.add(this)
            baseName = "tiny-navigation"
            isStatic = true
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.all {
            freeCompilerArgs += listOf("-Xbinary=gc=pmcs")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        iosMain.dependencies {

        }
    }
}

android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    namespace = "com.rakangsoftware.tiny.navigation"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}

publishing {
    repositories {
        maven {
            name = "oss"
            val releasesRepoUrl =
                uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl =
                uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = properties["ossrhUsername"].toString() ?: ""
                password = properties["ossrhPassword"].toString() ?: ""
            }
        }
    }

    publications {
        withType<MavenPublication> {
            pom {
                name.set("Navigation")
                description.set("A small KMP navigation library")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                url.set("https://github.com/pererikbergman/tiny-suite")
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/pererikbergman/tiny-suite/issues")
                }
                scm {
                    connection.set("https://github.com/pererikbergman/tiny-suite.git")
                    url.set("https://github.com/pererikbergman/tiny-suite")
                }
                developers {
                    developer {
                        name.set("Per-Erik Bergman")
                        email.set("bergman@uncle.se")
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications)
}