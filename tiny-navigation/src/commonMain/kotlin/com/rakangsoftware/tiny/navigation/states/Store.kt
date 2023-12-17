package com.rakangsoftware.tiny.navigation.states

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

val store = CoroutineScope(SupervisorJob()).createStore()

sealed interface Action {
    object OnBackPressed: Action
    data class Deeplink(val incomingURL:String): Action
}

interface Store {
    fun send(action: Action)
    val events: SharedFlow<Action>
}

fun CoroutineScope.createStore(): Store {
    val events = MutableSharedFlow<Action>()

    return object : Store {
        override fun send(action: Action) {
            launch {
                events.emit(action)
            }
        }
        override val events: SharedFlow<Action> = events.asSharedFlow()
    }
}