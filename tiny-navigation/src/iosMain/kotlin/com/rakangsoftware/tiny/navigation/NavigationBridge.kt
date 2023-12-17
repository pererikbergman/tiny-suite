package com.rakangsoftware.tiny.navigation

import com.rakangsoftware.tiny.navigation.states.Action
import com.rakangsoftware.tiny.navigation.states.store

fun onBackGesture() {
    store.send(Action.OnBackPressed)
}

fun onDeeplink(deepLinkUri:String) {
    store.send(Action.Deeplink(deepLinkUri))
}