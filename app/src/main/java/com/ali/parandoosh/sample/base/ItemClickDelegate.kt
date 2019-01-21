package com.ali.parandoosh.sample.base

import android.view.View


interface ItemClickDelegate {
    fun onItemClick(position: Int, viewType: Int, view: View?)
}