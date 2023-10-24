package com.izaber.project999.subscription.presentation

import com.izaber.project999.dashboard.DashboardScreen
import com.izaber.project999.fakes.FakeHandleDeath
import com.izaber.project999.fakes.FakeObservable
import com.izaber.project999.fakes.FakeSaveAndRestore
import com.izaber.project999.fakes.FakeSubscriptionInteractor
import com.izaber.project999.fakes.common.FakeClearRepresentative
import com.izaber.project999.fakes.common.FakeNavigation
import org.junit.Before
import org.junit.Test

internal class SubscriptionRepresentativeTest {

    private lateinit var representative: SubscriptionRepresentative
    private lateinit var deathHandler: FakeHandleDeath
    private lateinit var observable: FakeObservable
    private lateinit var clear: FakeClearRepresentative
    private lateinit var subscribeInteractor: FakeSubscriptionInteractor
    private lateinit var navigation: FakeNavigation

    @Before
    fun setup() {
        deathHandler = FakeHandleDeath.Base()
        observable = FakeObservable.Base()
        clear = FakeClearRepresentative.Base()
        subscribeInteractor = FakeSubscriptionInteractor.Base()
        navigation = FakeNavigation.Base()

        representative = SubscriptionRepresentative.Base(
            deathHandler = deathHandler,
            observable = observable,
            clear = clear,
            subscribeInteractor = subscribeInteractor,
            navigation = navigation
        )
    }

    @Test
    fun `main scenario`() {
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        deathHandler.checkFirstOpeningCalled(1)
        observable.checkUiState(SubscriptionUiState.Initial)

        val callBack = object : SubscriptionCallBack {
            override fun update(data: SubscriptionUiState) = Unit
        }

        representative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        representative.subscribe()
        observable.checkUiState(SubscriptionUiState.Loading)
        subscribeInteractor.checkSubscribeCalledTimes(1)
        subscribeInteractor.pingCallback()
        observable.checkUiState(SubscriptionUiState.Success)

        representative.observed()
        observable.checkClearCalled()

        representative.finish()
        clear.checkClearCalledWith(SubscriptionRepresentative::class.java)
        navigation.checkScreenUpdated(DashboardScreen)

        representative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptySubscriptionObserver)
    }

    @Test
    fun `save and restore`() {
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        deathHandler.checkFirstOpeningCalled(1)
        observable.checkUiState(SubscriptionUiState.Initial)
        observable.checkUpdateCalledCount(1)

        val callBack = object : SubscriptionCallBack {
            override fun update(data: SubscriptionUiState) = Unit
        }

        // start observing
        representative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        // save
        representative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptySubscriptionObserver)
        representative.saveState(saveAndRestore)

        // restore
        representative.init(saveAndRestore)
        deathHandler.checkFirstOpeningCalled(1)
        observable.checkUpdateCalledCount(1)
    }

    @Test
    fun `test death after loading`() {
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        deathHandler.checkFirstOpeningCalled(1)
        observable.checkUiState(SubscriptionUiState.Initial)

        val callBack = object : SubscriptionCallBack {
            override fun update(data: SubscriptionUiState) = Unit
        }

        representative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        representative.subscribe()
        observable.checkUiState(SubscriptionUiState.Loading)
        subscribeInteractor.checkSubscribeCalledTimes(1)
        representative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptySubscriptionObserver)
        representative.saveState(saveAndRestore)

        // process death
        setup()

        representative.init(saveAndRestore)
        deathHandler.checkFirstOpeningCalled(0)
        observable.checkUiState(SubscriptionUiState.Empty)
        observable.checkUpdateCalledCount(0)
        subscribeInteractor.checkSubscribeCalledTimes(1)
    }

    @Test
    fun `test death after success`() {
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        deathHandler.checkFirstOpeningCalled(1)
        observable.checkUiState(SubscriptionUiState.Initial)

        val callBack = object : SubscriptionCallBack {
            override fun update(data: SubscriptionUiState) = Unit
        }

        representative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        representative.subscribe()
        observable.checkUiState(SubscriptionUiState.Loading)
        subscribeInteractor.checkSubscribeCalledTimes(1)
        subscribeInteractor.pingCallback()
        observable.checkUiState(SubscriptionUiState.Success)
        representative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptySubscriptionObserver)
        representative.saveState(saveAndRestore)

        // process death
        setup()

        representative.init(saveAndRestore)
        deathHandler.checkFirstOpeningCalled(0)
        observable.checkUiState(SubscriptionUiState.Success)
        observable.checkUpdateCalledCount(1)
        subscribeInteractor.checkSubscribeCalledTimes(0)
    }

    @Test
    fun `test death after success observed`() {
        val saveAndRestore = FakeSaveAndRestore.Base()
        representative.init(saveAndRestore)
        deathHandler.checkFirstOpeningCalled(1)
        observable.checkUiState(SubscriptionUiState.Initial)

        val callBack = object : SubscriptionCallBack {
            override fun update(data: SubscriptionUiState) = Unit
        }

        representative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        representative.subscribe()
        observable.checkUiState(SubscriptionUiState.Loading)
        subscribeInteractor.checkSubscribeCalledTimes(1)
        subscribeInteractor.pingCallback()
        observable.checkUiState(SubscriptionUiState.Success)

        representative.observed()
        observable.checkClearCalled()
        representative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptySubscriptionObserver)
        representative.saveState(saveAndRestore)

        // process death
        setup()

        representative.init(saveAndRestore)
        deathHandler.checkFirstOpeningCalled(0)
        observable.checkUiState(SubscriptionUiState.Empty)
        observable.checkUpdateCalledCount(1)
        subscribeInteractor.checkSubscribeCalledTimes(0)
    }
}