package com.izaber.project999.main

import com.izaber.project999.core.Core
import com.izaber.project999.core.Module

class MainModule(
    private val core: Core
) : Module<MainRepresentative> {
    override fun representative(): MainRepresentative {
        return MainRepresentative.Base(core.navigation())
    }
}