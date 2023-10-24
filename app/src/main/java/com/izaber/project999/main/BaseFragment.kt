package com.izaber.project999.main

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.izaber.project999.core.ProvideRepresentative
import com.izaber.project999.core.Representative

abstract class BaseFragment<T : Representative<*>>(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    protected lateinit var representative: T

    protected abstract val clasz: Class<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        representative = (requireActivity() as ProvideRepresentative).provideRepresentative(clasz)
    }
}