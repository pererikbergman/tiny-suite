package com.rakangsoftware.tiny.navigation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform