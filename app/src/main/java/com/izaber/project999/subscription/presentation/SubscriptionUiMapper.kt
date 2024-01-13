package com.izaber.project999.subscription.presentation

import com.izaber.project999.core.UiUpdate
import com.izaber.project999.subscription.domain.SubscriptionResult

class SubscriptionUiMapper(
    private val observable: UiUpdate<SubscriptionUiState>
) : SubscriptionResult.Mapper {
    override fun mapSuccess(canGoBackCallback: (Boolean) -> Unit) {
        observable.update(SubscriptionUiState.Success)
        canGoBackCallback.invoke(true)
    }
}