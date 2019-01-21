package com.ali.parandoosh.sample.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity<B : ViewDataBinding> : DaggerAppCompatActivity() {
    protected lateinit var binding: B
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        val arguments = intent.extras
        if (arguments != null) {
            onExtractParams(arguments)
        }
    }

    open fun onExtractParams(arguments: Bundle) {
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    protected inline fun showSnackbar(message: () -> String) {
        Snackbar.make(binding.root, message(), Snackbar.LENGTH_LONG).show()
    }


}