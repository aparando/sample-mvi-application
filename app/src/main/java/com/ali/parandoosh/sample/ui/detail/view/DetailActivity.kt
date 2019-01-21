package com.ali.parandoosh.sample.ui.detail.view

import android.os.Bundle
import com.ali.parandoosh.sample.R
import com.ali.parandoosh.sample.base.BaseActivity
import com.ali.parandoosh.sample.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val fragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(
                        DetailFragment.ARG_RESTAURANT_KEY,
                        intent.getParcelableExtra(DetailFragment.ARG_RESTAURANT_KEY)
                    )
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
        }
    }
}