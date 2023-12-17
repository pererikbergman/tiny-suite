package com.rakangsoftware.tiny.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable

/**
 * Builder class for constructing a navigation graph using NavController.
 * Allows for the definition of composable screens with custom transitions.
 */
class NavigationGraphBuilder(
    internal val navController: NavController
)

/**
 * Adds a composable screen to the navigation graph.
 *
 * @param route The unique identifier for the navigation screen.
 * @param enterTransition Transition used when entering this screen.
 * @param exitTransition Transition used when exiting this screen.
 * @param popEnterTransition Transition used when returning to this screen.
 * @param popExitTransition Transition used when leaving this screen in reverse.
 * @param content The composable content of the screen.
 * @throws IllegalArgumentException If the route is blank.
 */
fun NavigationGraphBuilder.composable(
    route: String,
    enterTransition: EnterTransition = DefaultAnimations.enterTransition,
    exitTransition: ExitTransition = DefaultAnimations.exitTransition,
    popEnterTransition: EnterTransition = DefaultAnimations.popEnterTransition,
    popExitTransition: ExitTransition = DefaultAnimations.popExitTransition,
    content: @Composable () -> Unit
) {
    require(route.isNotBlank()) { "Invalid route: Route cannot be blank." }

    navController.addContent(
        route,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition,
        content
    )
}
