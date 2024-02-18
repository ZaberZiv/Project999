package com.izaber.project999.custom_views.states

import android.os.Parcel
import android.os.Parcelable
import android.view.View

class VisibilityState : View.BaseSavedState {
    var visible: Int = View.VISIBLE

    fun save(view: View) {
        visible = view.visibility
    }

    fun restore(view: View) {
        view.visibility = visible
    }

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcel: Parcel) : super(parcel)

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(visible)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<VisibilityState> {
        override fun createFromParcel(source: Parcel): VisibilityState = VisibilityState(source)
        override fun newArray(size: Int): Array<VisibilityState?> = arrayOfNulls(size)
    }
}