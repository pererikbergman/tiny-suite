package com.rakangsoftware.tiny.navigation.deeplink

import androidx.compose.runtime.Composable

@Composable
expect fun DeeplinkHandler(onDeeplink: (d: Deeplink) -> Unit)