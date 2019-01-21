package com.ali.parandoosh.sample.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment


abstract class BaseFragment<B : ViewDataBinding> : DaggerFragment() {

    protected lateinit var binding: B
    protected var toolbar: Toolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun setTitle(@StringRes title: Int) {
        toolbar?.setTitle(title)
    }

    protected inline fun showSnackbar(message: () -> String) {
        Snackbar.make(binding.root, message(), Snackbar.LENGTH_LONG).show()
    }

    protected fun showSnackbar(message: () -> String, completion: () -> Unit) {
        Snackbar.make(binding.root, message(), Snackbar.LENGTH_LONG)
            .addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    completion()
                }
            }).show()
    }
}