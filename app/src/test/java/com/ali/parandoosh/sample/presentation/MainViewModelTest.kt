package com.ali.parandoosh.sample.presentation

import com.ali.parandoosh.sample.cache.test.factory.DataFactory.Factory.randomLong
import com.ali.parandoosh.sample.cache.test.factory.DataFactory.Factory.randomUuid
import com.ali.parandoosh.sample.domain.interactor.main.GetRestaurants
import com.ali.parandoosh.sample.domain.model.Restaurant
import com.ali.parandoosh.sample.presentation.main.MainIntent
import com.ali.parandoosh.sample.presentation.main.MainProcessor
import com.ali.parandoosh.sample.presentation.main.MainUIModel
import com.ali.parandoosh.sample.presentation.main.MainViewModel
import com.ali.parandoosh.sample.presentation.main.mapper.RestaurantMapper
import com.ali.parandoosh.sample.presentation.main.model.RestaurantView
import com.nhaarman.mockito_kotlin.KArgumentCaptor
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

@RunWith(JUnit4::class)
class MainViewModelTest {
    @Mock
    lateinit var mockGetRestaurants: GetRestaurants
    @Mock
    lateinit var mockMapper: RestaurantMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<Restaurant>>>

    private lateinit var viewModel: MainViewModel
    private lateinit var processor: MainProcessor

    @Before
    fun setUp() {
        captor = argumentCaptor()
        mockGetRestaurants = mock()
        mockMapper = mock()
        processor = MainProcessor(mockGetRestaurants)
        viewModel = MainViewModel(processor, mockMapper)
    }


    @Test
    fun loadIntentReturnsSuccess() {
        val list = makeRestaurantList(2)
        val viewList = makeRestaurantViewList(2)
        stubMapperMapToView(viewList[0], list[0])
        stubMapperMapToView(viewList[1], list[1])
        stubGet(Flowable.just(list))

        val testObserver = viewModel.states().test()
        viewModel.processIntents(Observable.just(MainIntent.LoadRestaurantsIntent))

        testObserver.assertValueAt(2) { it is MainUIModel.Success }
    }

    @Test
    fun loadIntentWhenSuccessIsNotInProgress() {
        val list = makeRestaurantList(2)
        val viewList = makeRestaurantViewList(2)
        stubMapperMapToView(viewList[0], list[0])
        stubMapperMapToView(viewList[1], list[1])
        stubGet(Flowable.just(list))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.LoadRestaurantsIntent))

        testObserver.assertValueAt(2) { !it.inProgress }
    }

    @Test
    fun loadIntentReturnsData() {
        val list = makeRestaurantList(2)
        val viewList = makeRestaurantViewList(2)
        stubMapperMapToView(viewList[0], list[0])
        stubMapperMapToView(viewList[1], list[1])
        stubGet(Flowable.just(list))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.LoadRestaurantsIntent))

        testObserver.assertValueAt(2) { it.restaurantView == viewList }
    }

    @Test
    fun loadIntentReturnsError() {
        stubGet(Flowable.error(RuntimeException()))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.LoadRestaurantsIntent))

        testObserver.assertValueAt(2) { it is MainUIModel.Failed }
    }

    @Test
    fun loadIntentWhenErrorIsNotInProgress() {
        stubGet(Flowable.error(RuntimeException()))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.LoadRestaurantsIntent))

        testObserver.assertValueAt(2) { !it.inProgress }
    }

    @Test
    fun loadBIntentWhenErrorContainsNoData() {
        stubGet(Flowable.error(RuntimeException()))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.LoadRestaurantsIntent))

        testObserver.assertValueAt(2) { it.restaurantView == null }
    }

    @Test
    fun loadIntentReturnsLoading() {
        stubGet(Flowable.error(RuntimeException()))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.LoadRestaurantsIntent))

        testObserver.assertValueAt(1) { it is MainUIModel.InProgress }
    }

    @Test
    fun loadIntentBeginsAsIdle() {
        stubGet(Flowable.error(RuntimeException()))
        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.LoadRestaurantsIntent))

        testObserver.assertValueAt(0) { it is MainUIModel.Idle }
    }

    @Test
    fun refreshIntentReturnsSuccess() {
        val list = makeRestaurantList(2)
        val viewList = makeRestaurantViewList(2)
        stubMapperMapToView(viewList[0], list[0])
        stubMapperMapToView(viewList[1], list[1])
        stubGet(Flowable.just(list))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.RefreshRestaurantsIntent))

        testObserver.assertValueAt(2) { it is MainUIModel.Success }
    }

    @Test
    fun refreshIntentWhenSuccessIsNotInProgress() {
        val list = makeRestaurantList(2)
        val viewList = makeRestaurantViewList(2)
        stubMapperMapToView(viewList[0], list[0])
        stubMapperMapToView(viewList[1], list[1])
        stubGet(Flowable.just(list))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.RefreshRestaurantsIntent))

        testObserver.assertValueAt(2) { !it.inProgress }
    }

    @Test
    fun refreshIntentReturnsData() {
        val list = makeRestaurantList(2)
        val viewList = makeRestaurantViewList(2)
        stubMapperMapToView(viewList[0], list[0])
        stubMapperMapToView(viewList[1], list[1])
        stubGet(Flowable.just(list))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.RefreshRestaurantsIntent))

        testObserver.assertValueAt(2) { it.restaurantView == viewList }
    }

    @Test
    fun refreshIntentReturnsError() {
        stubGet(Flowable.error(RuntimeException()))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.RefreshRestaurantsIntent))

        testObserver.assertValueAt(2) { it is MainUIModel.Failed }
    }

    @Test
    fun refreshIntentWhenErrorIsNotInProgress() {
        stubGet(Flowable.error(RuntimeException()))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.RefreshRestaurantsIntent))

        testObserver.assertValueAt(2) { !it.inProgress }
    }

    @Test
    fun refreshIntentWhenErrorContainsNoData() {
        stubGet(Flowable.error(RuntimeException()))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.RefreshRestaurantsIntent))

        testObserver.assertValueAt(2) { it.restaurantView == null }
    }

    @Test
    fun refreshIntentReturnsLoading() {
        stubGet(Flowable.error(RuntimeException()))

        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.RefreshRestaurantsIntent))

        testObserver.assertValueAt(1) { it is MainUIModel.InProgress }
    }

    @Test
    fun refreshIntentBeginsAsIdle() {
        stubGet(Flowable.error(RuntimeException()))
        val testObserver = viewModel.states().test()

        viewModel.processIntents(Observable.just(MainIntent.RefreshRestaurantsIntent))

        testObserver.assertValueAt(0) { it is MainUIModel.Idle }
    }


    private fun stubMapperMapToView(restaurantView: RestaurantView,
                                    restaurant: Restaurant) {
        whenever(mockMapper.mapToView(restaurant))
                .thenReturn(restaurantView)
    }

    private fun stubGet(flowable: Flowable<List<Restaurant>>) {
        whenever(mockGetRestaurants.execute(anyOrNull()))
                .thenReturn(flowable)
    }

    private fun makeRestaurantList(count: Int): List<Restaurant> {
        val restaurants = mutableListOf<Restaurant>()
        repeat(count) {
            restaurants.add(makeRestaurantModel())
        }
        return restaurants
    }

    private fun makeRestaurantModel(): Restaurant {
        return Restaurant(randomLong(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
    }

    private fun makeRestaurantViewList(count: Int): List<RestaurantView> {
        val restaurants = mutableListOf<RestaurantView>()
        repeat(count) {
            restaurants.add(makeRestaurantView())
        }
        return restaurants
    }

    private fun makeRestaurantView(): RestaurantView {
        return RestaurantView(randomUuid(), randomUuid(), randomUuid(), randomUuid())
    }
}