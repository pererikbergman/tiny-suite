package com.rakangsoftware.tiny.navigation.deeplink

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.rakangsoftware.tiny.navigation.states.Action
import com.rakangsoftware.tiny.navigation.states.store

@Composable
actual fun DeeplinkHandler(onDeeplink: (d: Deeplink) -> Unit) {
    var lastDeeplink by remember { mutableStateOf<Deeplink?>(null) }

    LaunchedEffect(true) {
        store.events.collect { action ->
            if (action !is Action.Deeplink)
                return@collect

            val deeplink = action.incomingURL.toDeeplink()
            deeplink?.let { deeplink ->
                if (deeplink != lastDeeplink) {
                    lastDeeplink = deeplink
                    onDeeplink(
                        deeplink
                    )
                }
            }
        }
    }
}
