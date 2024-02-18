package com.izaber.project999.custom_views.buttons

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.izaber.project999.custom_views.states.HideAndShow
import com.izaber.project999.custom_views.states.VisibilityState
import com.izaber.project999.utils.TAG

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr), HideAndShow {

    override fun onSaveInstanceState(): Parcelable? = super.onSaveInstanceState()?.let {
        Log.v(TAG, "CustomButton: onSaveInstanceState")
        val visibilityState = VisibilityState(it)
        visibilityState.save(this)
        return visibilityState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        Log.v(TAG, "CustomButton: onRestoreInstanceState")
        val visibilityState = state as VisibilityState?
        super.onRestoreInstanceState(visibilityState?.superState)
        visibilityState?.restore(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.v(TAG, "CustomButton: onAttachedToWindow")
    }

    override fun hide() {
        isVisible = false
    }

    override fun show() {
        isVisible = true
    }
}