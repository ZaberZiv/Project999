package com.izaber.project999.subscription.progress.presentation

import com.izaber.project999.core.UiUpdate
import com.izaber.project999.custom_views.states.HideAndShow
import com.izaber.project999.subscription.screen.presentation.SubscriptionInner
import java.io.Serializable

interface SubscriptionProgressUiState : Serializable, CanGoBack {
    override fun canGoBack() = true
    fun show(hideAndShow: HideAndShow)
    fun observed(representative: SubscriptionProgressRepresentative) = Unit
    fun restoreAfterDeath(
        representative: SubscriptionInner,
        observable: UiUpdate<SubscriptionProgressUiState>
    )

    object Show : SubscriptionProgressUiState {
        override fun canGoBack() = false
        override fun show(hideAndShow: HideAndShow) {
            hideAndShow.show()
        }

        override fun restoreAfterDeath(
            representative: SubscriptionInner,
            observable: UiUpdate<SubscriptionProgressUiState>
        ) {
            representative.subscribeInner()
        }
    }

    object Hide : SubscriptionProgressUiState {
        override fun show(hideAndShow: HideAndShow) {
            hideAndShow.hide()
        }

        override fun observed(representative: SubscriptionProgressRepresentative) =
            representative.observed()

        override fun restoreAfterDeath(
            representative: SubscriptionInner,
            observable: UiUpdate<SubscriptionProgressUiState>
        ) {
            observable.update(this)
        }
    }

    object Empty : SubscriptionProgressUiState {
        override fun show(hideAndShow: HideAndShow) = Unit
        override fun restoreAfterDeath(
            representative: SubscriptionInner,
            observable: UiUpdate<SubscriptionProgressUiState>
        ) = Unit
    }
}

interface CanGoBack {
    fun canGoBack(): Boolean
}