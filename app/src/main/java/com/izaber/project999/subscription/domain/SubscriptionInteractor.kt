package com.izaber.project999.subscription.domain

import com.izaber.project999.main.UserPremiumCache
import com.izaber.project999.utils.UnitFunction

interface SubscriptionInteractor {
    fun subscribe(callBack: UnitFunction)

    class Base(
        private val userPremiumCache: UserPremiumCache.Save
    ) : SubscriptionInteractor {
        override fun subscribe(callBack: UnitFunction) {
            Thread {
                Thread.sleep(10000)
                userPremiumCache.saveUserPremium()
                callBack()
            }.start()
        }
    }
}