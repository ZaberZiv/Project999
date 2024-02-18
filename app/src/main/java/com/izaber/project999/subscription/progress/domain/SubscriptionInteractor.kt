package com.izaber.project999.subscription.progress.domain

import com.izaber.project999.subscription.progress.domain.repository.SubscriptionRepository

interface SubscriptionInteractor {
    suspend fun subscribe(): SubscriptionResult

    suspend fun subscribeInternal(): SubscriptionResult

    class Base(
        private val repository: SubscriptionRepository
    ) : SubscriptionInteractor {
        override suspend fun subscribe(): SubscriptionResult {
            return if (repository.isPremiumUser())
                SubscriptionResult.Success
            else {
                repository.subscribe()
                SubscriptionResult.NoDataYet
            }
        }

        override suspend fun subscribeInternal(): SubscriptionResult {
            repository.subscribe()
            return SubscriptionResult.Success
        }
    }
}

interface SubscriptionResult {
    interface Mapper {
        fun mapSuccess()
    }

    fun map(mapper: Mapper)

    object Success : SubscriptionResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess()
        }
    }

    object NoDataYet : SubscriptionResult {
        override fun map(mapper: Mapper) = Unit
    }
}