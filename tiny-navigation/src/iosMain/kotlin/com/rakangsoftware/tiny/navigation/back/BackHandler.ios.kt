package com.rakangsoftware.tiny.navigation.back

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.rakangsoftware.tiny.navigation.states.Action
import com.rakangsoftware.tiny.navigation.states.store

@Composable
actual fun BackHandler(isEnabled: Boolean, onBack: () -> Unit) {
    LaunchedEffect(isEnabled) {
        store.events.collect { action ->
            if (action !is Action.OnBackPressed)
                return@collect

            if (isEnabled) {
                onBack()
            }
        }
    }
}