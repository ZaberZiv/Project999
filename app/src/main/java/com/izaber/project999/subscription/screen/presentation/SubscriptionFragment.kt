package com.izaber.project999.subscription.screen.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.izaber.project999.R
import com.izaber.project999.core.UiObserver
import com.izaber.project999.custom_views.buttons.CustomButton
import com.izaber.project999.main.BaseFragment
import com.izaber.project999.subscription.progress.presentation.SubscriptionProgressBar

class SubscriptionFragment :
    BaseFragment<SubscriptionRepresentative>(R.layout.fragment_subscription) {

    private lateinit var observer: UiObserver<SubscriptionUiState>

    private var progressBar: SubscriptionProgressBar? = null

    override val clasz = SubscriptionRepresentative::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subscriptionButton = view.findViewById<CustomButton>(R.id.subscribeButton)
        val finishButton = view.findViewById<CustomButton>(R.id.buttonSuccess)
        progressBar = view.findViewById(R.id.progressBar)

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    progressBar!!.comeback(representative)
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
                data.show(subscriptionButton, progressBar!!, finishButton)
            }
        }
        val restoreState = SaveAndRestoreSubscriptionUiState.Base(savedInstanceState)
        representative.init(restoreState)
        progressBar!!.init(restoreState.isEmpty())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        representative.saveState(SaveAndRestoreSubscriptionUiState.Base(outState))
    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(observer)
        progressBar!!.resume()
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
        progressBar!!.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progressBar = null
    }
}