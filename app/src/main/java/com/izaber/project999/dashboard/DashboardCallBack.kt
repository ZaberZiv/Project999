package com.izaber.project999.dashboard

import com.izaber.project999.core.UiObserver

interface DashboardCallBack : UiObserver<PremiumDashboardUiState>

object EmptyDashboardObserver: DashboardCallBack {
    override fun update(data: PremiumDashboardUiState) = Unit
}