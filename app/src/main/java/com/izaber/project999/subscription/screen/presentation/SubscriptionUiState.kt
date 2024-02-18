package com.izaber.project999.subscription.screen.presentation

import com.izaber.project999.custom_views.states.HideAndShow
import com.izaber.project999.subscription.progress.presentation.Observed
import com.izaber.project999.subscription.progress.presentation.Subscribe
import java.io.Serializable

interface SubscriptionUiState : Serializable {
    fun observed(representative: Observed) = representative.observed()

    fun restoreAfterDeath(
        observable: SubscriptionObservable
    ) = observable.update(this)

    fun show(subscribeButton: HideAndShow, progressBar: Subscribe, successButton: HideAndShow)

    object Initial : SubscriptionUiState {
        override fun show(
            subscribeButton: HideAndShow,
            progressBar: Subscribe,
            successButton: HideAndShow
        ) {
            subscribeButton.show()
            successButton.hide()
        }
    }

    object Loading : SubscriptionUiState {
        override fun show(
            subscribeButton: HideAndShow,
            progressBar: Subscribe,
            successButton: HideAndShow
        ) {
            subscribeButton.hide()
            progressBar.subscribe()
            successButton.hide()
        }
    }

    object Success : SubscriptionUiState {
        override fun show(
            subscribeButton: HideAndShow,
            progressBar: Subscribe,
            successButton: HideAndShow
        ) {
            subscribeButton.hide()
            successButton.show()
        }
    }

    object Empty : SubscriptionUiState {
        override fun observed(representative: Observed) = Unit
        override fun restoreAfterDeath(observable: SubscriptionObservable) = Unit
        override fun show(
            subscribeButton: HideAndShow,
            progressBar: Subscribe,
            successButton: HideAndShow
        ) = Unit
    }
}