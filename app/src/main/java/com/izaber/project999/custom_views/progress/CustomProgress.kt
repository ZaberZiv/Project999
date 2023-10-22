package com.izaber.project999.custom_views.progress

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.izaber.project999.custom_views.states.HideAndShow
import com.izaber.project999.custom_views.states.VisibilityState

class CustomProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ProgressBar(context, attrs, defStyleAttr), HideAndShow {

    override fun onSaveInstanceState(): Parcelable? = super.onSaveInstanceState()?.let {
        val visibilityState = VisibilityState(it)
        visibilityState.visible = visibility
        return visibilityState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val visibilityState = state as VisibilityState?
        super.onRestoreInstanceState(visibilityState?.superState)
        visibilityState?.let {
            visibility = it.visible
        }
    }

    override fun hide() {
        isVisible = false
    }

    override fun show() {
        isVisible = true
    }
}