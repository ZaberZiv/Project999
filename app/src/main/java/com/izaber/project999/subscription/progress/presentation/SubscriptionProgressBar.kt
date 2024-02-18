package com.izaber.project999.subscription.progress.presentation

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.izaber.project999.core.ProvideRepresentative
import com.izaber.project999.core.UiObserver
import com.izaber.project999.custom_views.states.HideAndShow

class SubscriptionProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ProgressBar(context, attrs, defStyleAttr), SubscriptionProgressActions, Subscribe {

    private val representative: SubscriptionProgressRepresentative by lazy {
        (context.applicationContext as ProvideRepresentative).provideRepresentative(
            SubscriptionProgressRepresentative::class.java
        )
    }

    private val observer: UiObserver<SubscriptionProgressUiState> =
        object : UiObserver<SubscriptionProgressUiState> {
            override fun update(data: SubscriptionProgressUiState) {
                data.show(this@SubscriptionProgressBar)
                data.observed(representative)
            }
        }

    override fun init(firstRun: Boolean) {
        representative.init(firstRun)
    }

    override fun resume() {
        representative.startGettingUpdates(observer)
    }

    override fun pause() {
        representative.stopGettingUpdates()
    }

    override fun subscribe() {
        representative.subscribe()
    }

    override fun hide() {
        isVisible = false
    }

    override fun show() {
        isVisible = true
    }

    override fun comeback(data: ComeBack<Boolean>) {
        representative.comeback(data)
    }

    override fun onSaveInstanceState(): Parcelable? = super.onSaveInstanceState()?.let {
        val state = SubscriptionProgressSavedState(it)
        state.save(this)
        representative.save(state)
        return state
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoreState = state as SubscriptionProgressSavedState
        super.onRestoreInstanceState(restoreState.superState)
        restoreState.restore(this)
        representative.restore(restoreState)

    }
}

interface ComeBack<T> {
    fun comeback(data: T)
}

interface SubscriptionProgressActions : HideAndShow, ComeBack<ComeBack<Boolean>>, Init {
    fun resume()
    fun pause()
}

interface Subscribe {
    fun subscribe()
}

interface Init {
    fun init(firstRun: Boolean)
}

interface Observed {
    fun observed()
}