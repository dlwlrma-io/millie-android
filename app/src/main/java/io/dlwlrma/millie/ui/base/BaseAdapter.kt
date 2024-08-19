package io.dlwlrma.millie.ui.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected val items = mutableListOf<T>()

    var onClickListener: ((T) -> Unit)? = null

    override fun getItemCount(): Int = items.size

    fun get(): List<T> = items

    fun getItem(position: Int): T = items[position]

    fun addItem(item: T) {
        items.add(item)

        val index = items.indexOf(item)
        notifyItemInserted(index)
    }

    fun addItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, items.count())
    }

    fun deleteItem(item: T) {
        val index = items.indexOf(item)
        items.remove(item)

        notifyItemRemoved(index)
    }

    fun clearItems() {
        val count = items.count()
        items.clear()

        notifyItemRangeRemoved(0, count)
    }

    open fun calDiff(newItems: List<T>) {
        val diffCallback = DiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}