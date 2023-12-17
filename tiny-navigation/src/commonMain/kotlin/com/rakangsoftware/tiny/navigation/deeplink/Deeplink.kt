package com.rakangsoftware.tiny.navigation.deeplink

data class Deeplink(
    val scheme: String,
    val host: String,
    val path: List<String>,
)
