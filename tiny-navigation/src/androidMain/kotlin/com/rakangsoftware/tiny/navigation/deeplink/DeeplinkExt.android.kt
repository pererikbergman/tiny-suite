package com.rakangsoftware.tiny.navigation.deeplink

import android.net.Uri

fun Uri.toDeeplink(): Deeplink? {
    val scheme = this.scheme ?: return null
    val host = this.host ?: return null

    if (this.pathSegments.isEmpty())
        return null

    return Deeplink(
        scheme, host, pathSegments
    )
}