package com.rakangsoftware.tiny.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition

actual object DefaultAnimations {
    actual val enterTransition: EnterTransition = EnterTransition.None
    actual val exitTransition: ExitTransition = ExitTransition.None
    actual val popEnterTransition: EnterTransition = EnterTransition.None
    actual val popExitTransition: ExitTransition = ExitTransition.None
}