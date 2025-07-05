package com.irisvpn.android.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext


interface IrisNavigation {
    fun navigate(screen: Screen)

    @Composable
    fun RenderCurrentScreen()
    fun backPressed(shouldCloseApplication: () -> Unit)
    fun currentScreen(): Screen
}

private class SimpleNavigation : IrisNavigation {
    private val _stack = mutableStateListOf<Screen>()

    init {
        _stack.add(Screen.Main())
    }

    override fun navigate(screen: Screen) {
        _stack.add(screen)
    }

    @Composable
    override fun RenderCurrentScreen() {
        _stack.last().content().invoke()
    }


    override fun backPressed(shouldCloseApplication: () -> Unit) {
        if (_stack.isNotEmpty()) {
            _stack.removeLastOrNull()
        } else shouldCloseApplication.invoke()
    }

    override fun currentScreen(): Screen = _stack.last()
}

private val staticIrisNavigation by lazy {
    SimpleNavigation()
}

@Composable
fun rememberIrisNavigation(): IrisNavigation {
    val context = LocalContext.current
    val state = remember(context) {
        staticIrisNavigation
    }
    return state
}


