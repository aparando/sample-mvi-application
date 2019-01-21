package com.ali.parandoosh.sample.base

import android.databinding.ViewDataBinding
import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ali.parandoosh.sample.BR


open class BaseViewHolder(
    private val binding: ViewDataBinding,
    private val clickDelegate: ItemClickDelegate? = null
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private var bindItem: Any? = null

    @CallSuper
    open fun bind(item: Any) {
        bindItem = item
        bindItem?.let { (it as? Initializable)?.initialize(binding.root.context) }
        binding.setVariable(BR.model, item)
        binding.setVariable(BR.holder, this)
        binding.root.setOnClickListener(this)
        binding.executePendingBindings()
    }

    @CallSuper
    open fun unbind() {
        bindItem?.let { (it as? Resetable)?.reset() }.also { bindItem = null }
        binding.root.setOnClickListener(null)
        binding.unbind()
    }

    override fun onClick(v: View?) {
        clickDelegate?.onItemClick(adapterPosition, itemViewType, v)
    }
}