File: `settings.gradle.kts`

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/pererikbergman/github-packages")
        }
        
        // or ...

        maven("https://maven.pkg.github.com/pererikbergman/github-packages")
    }
}
```

File: `shared/build.gradle.kts`

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.rakangsoftware.tiny:navigation:0.0.1")
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
        }
    }
}
```

File: `Navigation.kt`

```kotlin
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
}

@Composable
fun RootNavigation() {
    val controller = rememberNavController()

    NavigationHost(
        controller, Screen.Splash.route
    ) {
        addSplashScreen(controller)
        addHomeScreen(controller)
    }
}

fun NavigationGraphBuilder.addSplashScreen(controller: NavController) {
    composable(
        Screen.Splash.route,
    ) {
        val viewModel = getViewModel(
            key = "splash-screen",
            factory = viewModelFactory {
                SplashViewModel()
            }
        )

        SplashScreen(
            viewModel = viewModel
        ) {
            controller.navigate(Screen.Home.route)
        }
    }
}

fun NavigationGraphBuilder.addHomeScreen(controller: NavController) {
    composable(Screen.Home.route) {
        HomeScreen()
    }
}
```