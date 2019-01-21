package com.ali.parandoosh.sample.ui.detail.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ali.parandoosh.sample.R
import com.ali.parandoosh.sample.base.BaseFragment
import com.ali.parandoosh.sample.databinding.RestaurantDetailBinding
import com.ali.parandoosh.sample.ui.model.RestaurantViewModel

class DetailFragment : BaseFragment<RestaurantDetailBinding>() {
    private lateinit var restaurantDetail: RestaurantViewModel
    override fun getLayoutId(): Int = R.layout.restaurant_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = binding.detailToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        arguments?.let {
            if (it.containsKey(ARG_RESTAURANT_KEY)) {
                restaurantDetail = it.get(ARG_RESTAURANT_KEY) as RestaurantViewModel
            }
        }
        toolbar?.apply {
            setNavigationOnClickListener { activity?.onBackPressed() }
        }
        binding.model = restaurantDetail

    }

    companion object {
        const val ARG_RESTAURANT_KEY = "arg_restaurant_key"
    }


}