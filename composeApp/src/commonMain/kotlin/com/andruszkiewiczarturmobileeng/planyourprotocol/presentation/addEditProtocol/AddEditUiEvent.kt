package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol

sealed class AddEditUiEvent {
    data object BackToHomeScreen: AddEditUiEvent()
}