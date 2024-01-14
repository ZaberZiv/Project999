package com.izaber.project999.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.izaber.project999.MyCameraService
import com.izaber.project999.R
import com.izaber.project999.core.UiObserver
import com.izaber.project999.custom_views.buttons.CustomButton
import com.izaber.project999.custom_views.text_views.CustomTextView
import com.izaber.project999.main.BaseFragment

class DashboardFragment : BaseFragment<DashboardRepresentative>(R.layout.fragment_dashboard) {

    private lateinit var uiObserver: UiObserver<PremiumDashboardUiState>
    override val clasz: Class<DashboardRepresentative>
        get() = DashboardRepresentative::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<CustomTextView>(R.id.showPlayingTextView)
        val button = view.findViewById<CustomButton>(R.id.playButton)
        val stopButton = view.findViewById<CustomButton>(R.id.stopButton)

        button.setOnClickListener {
//            representative.play()
            Log.e("Ilya", "navigate to subscribe")

            Intent(requireActivity().applicationContext, MyCameraService::class.java).also {
                it.action = "START"
                requireActivity().startService(it)
            }
        }

        stopButton.setOnClickListener {
//            representative.play()
            Log.e("Ilya", "stopButton")

            Intent(requireActivity().applicationContext, MyCameraService::class.java).also {
                it.action = "STOP"
                requireActivity().startService(it)
            }
        }

        uiObserver = object : DashboardCallBack {
            override fun update(data: PremiumDashboardUiState) {
                data.show(button, textView)
                data.observed(representative)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(uiObserver)
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }
}