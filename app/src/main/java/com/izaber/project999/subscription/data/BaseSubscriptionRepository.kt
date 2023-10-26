package com.izaber.project999.subscription.data

import com.izaber.project999.main.UserPremiumCache
import com.izaber.project999.subscription.domain.repository.SubscriptionRepository

class BaseSubscriptionRepository(
    private val cloudDataSource: SubscriptionCloudDataSource,
    private val userPremiumCache: UserPremiumCache.Save
) : SubscriptionRepository {

    override suspend fun subscribe() {
        cloudDataSource.subscribe()
        userPremiumCache.saveUserPremium()
    }
}