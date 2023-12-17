import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    id("maven-publish")
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
            baseName = "navigation"
            isStatic = true
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.all {
            freeCompilerArgs += listOf("-Xgc=cms")
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
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.rakangsoftware.tiny"
            artifactId = "navigation"
            version = "0.0.1"

            from(components["kotlin"]) // Adjust this based on your project's components

            pom {
                name.set("Tiny Navigation")
                description.set("A description of your library")
                url.set("https://yourwebsite.com")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("yourID")
                        name.set("Your Name")
                        email.set("your@email.com")
                    }
                }

                scm {
                    url.set("https://github.com/pererikbergman/tiny-suite")
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/pererikbergman/tiny-suite")
            credentials {
                username = project.findProperty("GPR_USER") as String? ?: System.getenv("GPR_USER")
                password =
                    project.findProperty("GPR_API_KEY") as String? ?: System.getenv("GPR_API_KEY")
            }
        }
    }
}