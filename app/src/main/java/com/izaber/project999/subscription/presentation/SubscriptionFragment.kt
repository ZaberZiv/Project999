package com.izaber.project999.subscription.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.izaber.project999.R
import com.izaber.project999.core.UiObserver
import com.izaber.project999.custom_views.buttons.CustomButton
import com.izaber.project999.custom_views.progress.CustomProgress
import com.izaber.project999.main.BaseFragment

class SubscriptionFragment :
    BaseFragment<SubscriptionRepresentative>(R.layout.fragment_subscription) {

    private lateinit var observer: UiObserver<SubscriptionUiState>

    override val clasz: Class<SubscriptionRepresentative>
        get() = SubscriptionRepresentative::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val subscriptionButton = view.findViewById<CustomButton>(R.id.subscribeButton)
        val finishButton = view.findViewById<CustomButton>(R.id.buttonSuccess)
        val progressBar = view.findViewById<CustomProgress>(R.id.progressBar)

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    representative.comeback()
                }
            })

        subscriptionButton.setOnClickListener {
            representative.subscribe()
        }

        finishButton.setOnClickListener {
            representative.finish()
        }

        observer = object : SubscriptionCallBack {
            override fun update(data: SubscriptionUiState) {
                data.observed(representative)
                data.show(subscriptionButton, progressBar, finishButton)
            }
        }

        representative.init(SaveAndRestoreSubscriptionUiState.Base(savedInstanceState))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        representative.saveState(SaveAndRestoreSubscriptionUiState.Base(outState))
    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(observer)
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }
}