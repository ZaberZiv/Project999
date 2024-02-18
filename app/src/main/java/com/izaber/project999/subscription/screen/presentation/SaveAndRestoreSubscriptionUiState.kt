package com.izaber.project999.subscription.screen.presentation

import android.os.Bundle
import com.izaber.project999.core.SaveAndRestoreState

interface SaveAndRestoreSubscriptionUiState {
    interface Save : SaveAndRestoreState.Save<SubscriptionUiState>
    interface Restore : SaveAndRestoreState.Restore<SubscriptionUiState>
    interface Mutable : Save, Restore

    class Base(
        bundle: Bundle?
    ) : SaveAndRestoreState.Abstract<SubscriptionUiState>(
        bundle = bundle,
        key = KEY,
        clasz = SubscriptionUiState::class.java,
    ), Mutable

    companion object {
        private const val KEY = "SubscriptionUiState_RESTORE_KEY"
    }
}