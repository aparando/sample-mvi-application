package com.ali.parandoosh.sample.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


open class BaseAdapter<I : Item>(private val clickDelegate: ItemClickDelegate) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private val items = mutableListOf<I>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return BaseViewHolder(binding, clickDelegate)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getLayoutId()

    override fun onViewRecycled(holder: BaseViewHolder) {
        holder.unbind()
    }

    fun getItem(position: Int): I = items[position]

    fun update(items: List<I>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun add(item: I) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun add(index: Int, item: I) {
        items.add(index, item)
        notifyItemInserted(index)
    }


}