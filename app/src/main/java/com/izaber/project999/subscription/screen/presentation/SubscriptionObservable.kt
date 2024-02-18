package com.izaber.project999.subscription.screen.presentation

import com.izaber.project999.core.UiObservable

interface SubscriptionObservable : UiObservable<SubscriptionUiState>, SaveSubscriptionUiState {
    class Base : UiObservable.Base<SubscriptionUiState>(SubscriptionUiState.Empty),
        SubscriptionObservable {
        override fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save) {
            saveState.save(cache)
        }
    }
}