package com.izaber.project999.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.izaber.project999.R
import com.izaber.project999.core.ProvideRepresentative
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

        button.setOnClickListener {
            representative.play()
            Log.e("Ilya", "navigate to subscribe")
        }

        uiObserver = object : UiObserver<PremiumDashboardUiState> {
            override fun update(data: PremiumDashboardUiState) {
                data.show(button, textView)
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