package com.rakangsoftware.tiny.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
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
        val content: @Composable (NavBackStackEntry) -> Unit // HERE
    )

    private fun findTarget(route: String): NavigationTarget? = targetHashMap[route]

    data class NavBackStackEntry(
        val target: NavigationTarget,
        var bundle: TinyBundle = TinyBundle(),
    ) {
        fun add(params: Map<String, Any>) {
            this.bundle.add(params)
        }
    }

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
        val result = getTemplateFormValueString(route) ?: return

        val (routeTemplate, params) = result

        findTarget(routeTemplate)?.let { navigationTarget ->
            val entry = NavBackStackEntry(target = navigationTarget)
            entry.add(params)

            isBackNavigation.value = false
            backStackScreens.add(entry)
            currentScreen.value = entry
        }
    }

    private fun getTemplateFormValueString(route: String): Pair<String, Map<String, Any>>? {
        targetHashMap.forEach { (key, value) ->
            getParamsOrNull(route, value.route)?.let {
                return Pair(value.route, it)
            }
        }

        return null
    }

    /**
     * Handles back navigation logic.
     */
    fun navigateBack() {
        if (backStackScreens.isNotEmpty()) {
            isBackNavigation.value = true
            currentScreen.value = backStackScreens.removeLast()
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
        content: @Composable (NavBackStackEntry) -> Unit // HERE
    ) {
        if (targetHashMap.containsKey(route)) {
            return
        }

        targetHashMap[route] = NavigationTarget(
            route,
            enterTransition,
            exitTransition,
            popEnterTransition,
            popExitTransition,
            content
        )
    }

    /**
     * Composable function to render the current navigation target.
     */
    @Composable
    operator fun invoke() {
        if (currentScreen.value == null) {
            navigate(startDestination)
        }

        currentScreen.value?.let {
            val isBackNav by isBackNavigation
            AnimatedContent(
                targetState = it.target,
                transitionSpec = {
                    if (isBackNav) {
                        it.target.popEnterTransition togetherWith it.target.popExitTransition
                    } else {
                        it.target.enterTransition togetherWith it.target.exitTransition
                    }
                }
            ) { targetState ->
                targetState.content(it)
            }
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

fun getParamsOrNull(valueString: String, templateString: String): Map<String, Any>? {
    if (valueString.isBlank() || templateString.isBlank()) return null

    // Split the strings into path segments
    val valueSegments = valueString.split("/")
    val templateSegments = templateString.split("/")

    // Check if segments count matches
    if (valueSegments.size != templateSegments.size) return null

    val resultMap = mutableMapOf<String, Any>()

    // Iterate through the template segments
    for (i in templateSegments.indices) {
        val templateSegment = templateSegments[i]
        val valueSegment = valueSegments[i]

        if (isStatic(templateSegment)) {
            // Static segment: directly compare with value segment
            if (templateSegment != valueSegment) return null
        } else {
            // Dynamic segment: extract key and map it to the value segment
            val key = templateSegment.trim('{', '}')
            resultMap[key] = getAsPrimitiveType(valueSegment)
        }
    }

    return resultMap
}

fun isStatic(value: String): Boolean =
    !(value.startsWith("{") && value.endsWith("}"))

fun getAsPrimitiveType(value: String): Any =
    value.toIntOrNull()
        ?: value.toDoubleOrNull()
        ?: value.toBooleanStrictOrNull()
        ?: value