package com.izaber.project999.subscription.domain.repository

interface SubscriptionRepository {
    fun subscribe()
    suspend fun subscribeInternal()
    fun isPremiumUser(): Boolean
}