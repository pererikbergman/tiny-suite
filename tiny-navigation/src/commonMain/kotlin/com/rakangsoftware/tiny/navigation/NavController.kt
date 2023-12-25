package com.rakangsoftware.tiny.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Custom NavController for managing navigation in a Compose application.
 * Handles the navigation stack and transitions between screens.
 */
class NavController {

    private var startDestination = ""
    private var backStackScreens: MutableList<NavBackStackEntry> = mutableListOf()
    private var currentScreen: MutableState<NavBackStackEntry?> = mutableStateOf(null)
    private val targetHashMap: HashMap<String, NavigationTarget> = hashMapOf()
    private var isBackNavigation: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Represents a navigation destination with associated transitions and content.
     */
    data class NavigationTarget(
        val route: String,
        val enterTransition: EnterTransition,
        val exitTransition: ExitTransition,
        val popEnterTransition: EnterTransition,
        val popExitTransition: ExitTransition,
        val content: @Composable () -> Unit
    )

    private fun findTarget(route: String): NavigationTarget? = targetHashMap[route]

    data class NavBackStackEntry(
        val target: NavigationTarget
    )

    /**
     * Sets the start destination of the navigation.
     */
    internal fun setStartDestination(startDestination: String) {
        this.startDestination = startDestination
    }

    /**
     * Navigates to the specified route.
     */
    fun navigate(route: String, builder: NavOptionBuilder.() -> Unit = {}) {
        if (route == currentScreen.value?.target?.route) {
            return
        }

        findTarget(route)?.let { navigationTarget ->
            val entry = NavBackStackEntry(target = navigationTarget)

            isBackNavigation.value = false
            backStackScreens.add(entry)
            currentScreen.value = entry
        }
    }

    /**
     * Handles back navigation logic.
     */
    fun navigateBack() {
        if (backStackScreens.isNotEmpty()) {
            isBackNavigation.value = true
            currentScreen.value = backStackScreens.last()
            backStackScreens.remove(currentScreen.value)
            currentScreen.value = backStackScreens.last()
        }
    }

    /**
     * Adds content to a specific route.
     */
    internal fun addContent(
        route: String,
        enterTransition: EnterTransition,
        exitTransition: ExitTransition,
        popEnterTransition: EnterTransition,
        popExitTransition: ExitTransition,
        content: @Composable () -> Unit
    ) {
        if (targetHashMap.containsKey(route)) {
            return
        }

        targetHashMap[route] = NavigationTarget(
            route, enterTransition, exitTransition, popEnterTransition, popExitTransition, content
        )
    }

    /**
     * Composable function to render the current navigation target.
     */
    @Composable
    operator fun invoke() {
        var current = currentScreen.value
        if (current == null) {
            navigate(startDestination)
            current = currentScreen.value
        }
        current?.let {
            val isBackNav by isBackNavigation
            val navigationTarget = targetHashMap[it.target.route] ?: return

            AnimatedNavigationContent(navigationTarget, isBackNav)
        }
    }
}

/**
 * Composable function to remember an instance of NavController.
 */
@Composable
fun rememberNavController(): NavController {
    return remember { NavController() }
}

/**
 * Composable function to handle animated content during navigation.
 */
@Composable
private fun AnimatedNavigationContent(
    navigationTarget: NavController.NavigationTarget,
    isBackNav: Boolean
) {
    AnimatedContent(
        targetState = navigationTarget,
        transitionSpec = {
            if (isBackNav) {
                navigationTarget.popEnterTransition togetherWith navigationTarget.popExitTransition
            } else {
                navigationTarget.enterTransition togetherWith navigationTarget.exitTransition
            }
        }
    ) { targetState ->
        targetState.content()
    }
}
