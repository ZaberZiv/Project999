package com.izaber.project999.subscription.screen.presentation

import com.izaber.project999.core.UiObserver

interface SubscriptionCallBack : UiObserver<SubscriptionUiState>

object EmptySubscriptionObserver : SubscriptionCallBack {
    override fun update(data: SubscriptionUiState) = Unit
}