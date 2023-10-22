package com.izaber.project999.subscription

import com.izaber.project999.custom_views.states.HideAndShow
import java.io.Serializable

interface SubscriptionUiState : Serializable {
    fun restoreAfterDeath(
        subscriptionInner: SubscriptionInner,
        observable: SubscriptionObservable
    ) = observable.update(this)

    fun show(subscribeButton: HideAndShow, progressBar: HideAndShow, successButton: HideAndShow)
    fun observed(representative: SubscriptionObserved) = representative.observed()

    object Initial : SubscriptionUiState {
        override fun show(
            subscribeButton: HideAndShow,
            progressBar: HideAndShow,
            successButton: HideAndShow
        ) {
            subscribeButton.show()
            progressBar.hide()
            successButton.hide()
        }
    }

    object Loading : SubscriptionUiState {
        override fun show(
            subscribeButton: HideAndShow,
            progressBar: HideAndShow,
            successButton: HideAndShow
        ) {
            subscribeButton.hide()
            progressBar.show()
            successButton.hide()
        }

        override fun restoreAfterDeath(
            subscriptionInner: SubscriptionInner,
            observable: SubscriptionObservable
        ) {
            subscriptionInner.subscribeInner()
        }
    }

    object Success : SubscriptionUiState {
        override fun show(
            subscribeButton: HideAndShow,
            progressBar: HideAndShow,
            successButton: HideAndShow
        ) {
            subscribeButton.hide()
            progressBar.hide()
            successButton.show()
        }
    }

    object Empty : SubscriptionUiState {
        override fun show(
            subscribeButton: HideAndShow,
            progressBar: HideAndShow,
            successButton: HideAndShow
        ) = Unit
    }
}