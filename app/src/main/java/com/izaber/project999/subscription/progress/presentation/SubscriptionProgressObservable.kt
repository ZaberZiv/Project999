package com.izaber.project999.subscription.progress.presentation

import com.izaber.project999.core.UiObservable

interface SubscriptionProgressObservable : UiObservable<SubscriptionProgressUiState>, CanGoBack {
    fun save(save: SaveAndRestoreSubscriptionUiState.Save)
    class Base : UiObservable.Base<SubscriptionProgressUiState>(SubscriptionProgressUiState.Empty),
        SubscriptionProgressObservable {
        override fun canGoBack() = cache.canGoBack()
        override fun save(save: SaveAndRestoreSubscriptionUiState.Save) = save.save(cache)
    }
}