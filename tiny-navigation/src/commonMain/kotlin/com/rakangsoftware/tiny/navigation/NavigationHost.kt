package com.rakangsoftware.tiny.navigation

import androidx.compose.runtime.Composable
import com.rakangsoftware.tiny.navigation.deeplink.DeeplinkHandler

/**
 * Sets up the navigation host with the specified start destination and navigation graph.
 *
 * @param navController The NavController managing the navigation.
 * @param startDestination The start destination route in the navigation graph.
 * @param builder The builder block to define the navigation graph.
 */
@Composable
fun NavHost(
    navController: NavController,
    startDestination: String,
    builder: NavigationGraphBuilder.() -> Unit
) {
    navController.setStartDestination(startDestination)

    val navigationGraphBuilder = NavigationGraphBuilder(navController)
    navigationGraphBuilder.builder()

    ActualNavigationHost(navController)
}

/**
 * Internal function to render the actual navigation host.
 * This function is responsible for handling the navigation state and rendering the current screen.
 *
 * @param navController The NavController used for navigation.
 */
@Composable
private fun ActualNavigationHost(
    navController: NavController
) {
    // Handling deeplinks can be integrated here if needed
    DeeplinkHandler { deeplink ->
        println("NavigationHost::DeeplinkHandler: $deeplink")
    }

    navController()
}
