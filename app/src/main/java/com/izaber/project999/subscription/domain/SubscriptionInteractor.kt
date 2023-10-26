package com.izaber.project999.subscription.domain

import com.izaber.project999.subscription.domain.repository.SubscriptionRepository

interface SubscriptionInteractor {
    suspend fun subscribe()

    class Base(
        private val subscriptionRepository: SubscriptionRepository
    ) : SubscriptionInteractor {
        override suspend fun subscribe() {
            subscriptionRepository.subscribe()
        }
    }
}