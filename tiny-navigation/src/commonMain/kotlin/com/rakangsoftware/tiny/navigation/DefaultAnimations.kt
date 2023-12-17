package com.rakangsoftware.tiny.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition

expect object DefaultAnimations {
    val enterTransition: EnterTransition
    val exitTransition: ExitTransition
    val popEnterTransition: EnterTransition
    val popExitTransition: ExitTransition
}