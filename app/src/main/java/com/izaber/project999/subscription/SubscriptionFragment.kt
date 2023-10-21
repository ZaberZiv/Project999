package com.izaber.project999.subscription

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.izaber.project999.R
import com.izaber.project999.main.BaseFragment

class SubscriptionFragment :
    BaseFragment<SubscriptionRepresentative>(R.layout.fragment_subscription) {
    override val clasz: Class<SubscriptionRepresentative>
        get() = SubscriptionRepresentative::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.subscribeButton)
        button.setOnClickListener {
            representative.subscribe()
        }
    }
}