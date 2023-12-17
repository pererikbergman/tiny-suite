package com.rakangsoftware.tiny.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

actual object DefaultAnimations {

    private const val DURATION_IN_MS: Int = 350

    actual val enterTransition: EnterTransition = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(DURATION_IN_MS)
    )
    actual val exitTransition: ExitTransition = slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(DURATION_IN_MS)
    )
    actual val popEnterTransition: EnterTransition = slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(DURATION_IN_MS)
    )
    actual val popExitTransition: ExitTransition = slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(DURATION_IN_MS)
    )
}