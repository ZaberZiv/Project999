package com.izaber.project999.dashboard

import com.izaber.project999.custom_views.states.HideAndShow

interface PremiumDashboardUiState {

    fun show(button: HideAndShow, textView: HideAndShow)

    object Playing : PremiumDashboardUiState {
        override fun show(button: HideAndShow, textView: HideAndShow) {
            button.hide()
            textView.show()
        }
    }
}