package com.izaber.project999.dashboard

import com.izaber.project999.core.UiObservable

interface PremiumDashboardObservable: UiObservable<PremiumDashboardUiState> {
    object Base : UiObservable.Base<PremiumDashboardUiState>(PremiumDashboardUiState.Empty),
        PremiumDashboardObservable
}