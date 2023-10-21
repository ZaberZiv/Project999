package com.izaber.project999.custom_views.buttons

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.izaber.project999.custom_views.states.HideAndShow
import com.izaber.project999.custom_views.states.VisibilityState

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr), HideAndShow {

    override fun onSaveInstanceState(): Parcelable? = super.onSaveInstanceState()?.let {
        val visibilityState = VisibilityState(it)
        visibilityState.visible = visibility
        return visibilityState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val visibilityState = state as VisibilityState?
        super.onRestoreInstanceState(visibilityState)
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