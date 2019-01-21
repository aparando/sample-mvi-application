package com.ali.parandoosh.sample.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ali.parandoosh.sample.R
import com.ali.parandoosh.sample.base.BaseActivity
import com.ali.parandoosh.sample.base.BaseAdapter
import com.ali.parandoosh.sample.base.ItemClickDelegate
import com.ali.parandoosh.sample.databinding.ActivityMainBinding
import com.ali.parandoosh.sample.presentation.base.BaseView
import com.ali.parandoosh.sample.presentation.main.MainIntent
import com.ali.parandoosh.sample.presentation.main.MainUIModel
import com.ali.parandoosh.sample.presentation.main.MainViewModel
import com.ali.parandoosh.sample.presentation.main.model.RestaurantView
import com.ali.parandoosh.sample.ui.detail.view.DetailActivity
import com.ali.parandoosh.sample.ui.detail.view.DetailFragment
import com.ali.parandoosh.sample.ui.mapper.RestaurantMapper
import com.ali.parandoosh.sample.ui.model.RestaurantViewModel
import com.ali.parandoosh.sample.utils.launch
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding>(), ItemClickDelegate,
    BaseView<MainIntent, MainUIModel> {
    override fun onItemClick(position: Int, viewType: Int, view: View?) {
        launch<DetailActivity> {
            val restaurantModel = restaurantAdapter.getItem(position)
            putExtra(DetailFragment.ARG_RESTAURANT_KEY, restaurantModel)
        }
    }

    private val compositeDisposable = CompositeDisposable()

    private val loadIntentPublisher = BehaviorRelay.create<MainIntent.LoadRestaurantsIntent>()

    private val refreshIntentPublisher = BehaviorRelay.create<MainIntent.RefreshRestaurantsIntent>()

    private val restaurantAdapter = BaseAdapter<RestaurantViewModel>(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mapper: RestaurantMapper
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)
        setupBrowseRecycler()

        addDisposable(mainViewModel.states().subscribe { render(it) })
        mainViewModel.processIntents(intents())
    }

    private fun setupBrowseRecycler() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = restaurantAdapter
            val dividerItemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

    }


    override fun intents(): Observable<MainIntent> {
        return Observable.merge(
            initialIntent(), loadIntentPublisher,
            refreshIntentPublisher
        )
    }

    private fun initialIntent(): Observable<MainIntent.InitialIntent> {
        return Observable.just(MainIntent.InitialIntent)
    }

    override fun render(state: MainUIModel) {
        when {
            state.inProgress -> {
                setupScreenForLoadingState()
            }
            state is MainUIModel.Failed -> {
                binding.progress.visibility = View.GONE
                showSnackbar { getString(R.string.label_error_result) }

            }
            state is MainUIModel.Success -> {
                setupScreenForSuccess(state.restaurantView)
            }
        }
    }

    private fun setupScreenForLoadingState() {
        binding.apply {
            progress.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    private fun setupScreenForSuccess(data: List<RestaurantView>?) {
        binding.apply {
            progress.visibility = View.GONE
            if (data != null && data.isNotEmpty()) {
                restaurantAdapter.update(data.map { mapper.mapToViewModel(it) })
                recyclerView.visibility = View.VISIBLE
            } else {
                showSnackbar { getString(R.string.label_empty_result) }
            }
        }
    }


    override fun getLayoutId(): Int = R.layout.activity_main

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

}