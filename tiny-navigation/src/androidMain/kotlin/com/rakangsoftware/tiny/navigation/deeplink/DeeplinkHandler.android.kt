package com.rakangsoftware.tiny.navigation.deeplink

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun DeeplinkHandler(onDeeplink: (d: Deeplink) -> Unit) {
    val activity = LocalContext.current as Activity
    val intent = activity.intent

    if (intent.action != Intent.ACTION_VIEW) return

    val deepLinkUri: Uri = intent.data ?: return
    LaunchedEffect(true) {
        deepLinkUri.toDeeplink()?.let { deeplink ->
            onDeeplink(deeplink)
        }
    }
}