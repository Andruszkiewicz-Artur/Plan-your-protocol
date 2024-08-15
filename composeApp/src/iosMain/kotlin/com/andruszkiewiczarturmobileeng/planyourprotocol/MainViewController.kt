package com.andruszkiewiczarturmobileeng.planyourprotocol

import androidx.compose.ui.window.ComposeUIViewController
import com.andruszkiewiczarturmobileeng.planyourprotocol.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}