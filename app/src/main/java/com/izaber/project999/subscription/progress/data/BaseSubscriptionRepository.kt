package com.izaber.project999.subscription.progress.data

import com.izaber.project999.main.UserPremiumCache
import com.izaber.project999.subscription.progress.domain.repository.SubscriptionRepository

class BaseSubscriptionRepository(
    private val foregroundServiceWrapper: ForegroundServiceWrapper,
    private val cloudDataSource: SubscriptionCloudDataSource,
    private val userPremiumCache: UserPremiumCache.Mutable
) : SubscriptionRepository {

    override fun isPremiumUser() = userPremiumCache.isUserPremium()

    override fun subscribe() = foregroundServiceWrapper.start()

    override suspend fun subscribeInternal() {
        cloudDataSource.subscribe()
        userPremiumCache.saveUserPremium()
    }
}