package com.izaber.project999.subscription.progress.presentation

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class SubscriptionProgressSavedState : View.BaseSavedState,
    SaveAndRestoreSubscriptionUiState.Mutable {

    private var visible: Int = View.VISIBLE
    private var state: SubscriptionProgressUiState = SubscriptionProgressUiState.Empty

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcel: Parcel) : super(parcel) {
        visible = parcel.readInt()
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcel.readSerializable(
                null,
                SubscriptionProgressUiState::class.java
            )!!
        } else {
            parcel.readSerializable() as SubscriptionProgressUiState
        }
    }

    fun save(view: View) {
        visible = view.visibility
    }

    fun restore(view: View) {
        view.visibility = visible
    }

    override fun save(state: SubscriptionProgressUiState) {
        this.state = state
    }

    override fun restore(): SubscriptionProgressUiState {
        return state
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(visible)
        out.writeSerializable(state)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<SubscriptionProgressSavedState> {
        override fun createFromParcel(source: Parcel): SubscriptionProgressSavedState =
            SubscriptionProgressSavedState(source)

        override fun newArray(size: Int): Array<SubscriptionProgressSavedState?> =
            arrayOfNulls(size)
    }
}

interface SaveAndRestoreSubscriptionUiState {
    interface Save {
        fun save(state: SubscriptionProgressUiState)
    }

    interface Restore {
        fun restore(): SubscriptionProgressUiState
    }

    interface Mutable : Save, Restore
}