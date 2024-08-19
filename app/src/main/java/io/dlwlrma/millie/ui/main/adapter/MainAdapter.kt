package io.dlwlrma.millie.ui.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.dlwlrma.millie.BR
import io.dlwlrma.millie.R
import io.dlwlrma.millie.databinding.ItemRecyclerMainBinding
import io.dlwlrma.millie.domain.model.News
import io.dlwlrma.millie.ui.base.BaseAdapter

class MainAdapter : BaseAdapter<News>() {
    private var history = mutableListOf<Boolean>()

    override fun calDiff(newItems: List<News>) {
        super.calDiff(newItems)

        val count = items.count()
        history = MutableList(count) { false }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemRecyclerMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_recycler_main, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(items[position])
        }
    }

    inner class ViewHolder(private val binding: ItemRecyclerMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: News) {
            binding.run {
                setVariable(BR.news, item)
                executePendingBindings()

                root.setOnClickListener {
                    history[adapterPosition] = true
                    notifyItemChanged(adapterPosition)

                    onClickListener?.invoke(item)
                }

                if (history.isNotEmpty()) {
                    if (history[adapterPosition]) {
                        textTitle.setTextColor(Color.RED)
                    } else {
                        textTitle.setTextColor(Color.BLACK)
                    }
                }
            }
        }
    }
}
