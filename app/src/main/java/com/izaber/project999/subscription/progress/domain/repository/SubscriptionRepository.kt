package com.izaber.project999.subscription.progress.domain.repository

interface SubscriptionRepository {
    fun subscribe()
    suspend fun subscribeInternal()
    fun isPremiumUser(): Boolean
}