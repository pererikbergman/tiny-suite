package com.rakangsoftware.tiny.navigation.deeplink

fun String.toDeeplink(): Deeplink? {
    val incomingURL = this
    if (incomingURL.isBlank())
        return null

    if (!incomingURL.contains("://"))
        return null

    val strings = incomingURL.split("://")
    if (strings.size < 2)
        return null

    val scheme = strings.firstOrNull()
    if (scheme.isNullOrBlank())
        return null

    val hostAndPath = strings.drop(1).firstOrNull() ?: return null

    val hostAndPathStrings = hostAndPath.split("/")
    if (hostAndPathStrings.size < 2) {
        return null
    }

    val host = hostAndPathStrings.first()
    val path = hostAndPathStrings.drop(1)

    return Deeplink(
        scheme, host, path
    )
}
