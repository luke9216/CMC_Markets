package com.cmcmarkets.android.exercise.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T>(protected val dataList: MutableList<T>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Card layout
     *
     * @param context
     * @param viewType
     * @return
     */
    abstract fun getCardView(context: Context, viewType: Int): View

    /**
     * Binding of each card that would be in the recyclerview
     *
     * @param itemView
     * @param data
     * @param position
     */
    abstract fun bindData(itemView: View, data: T, position: Int)

    /**
     *
     * @return Click event - Card
     */
    abstract fun getItemClickListener(): ((T) -> Unit)?
    inner class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener{
                getItemClickListener()?.invoke(dataList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return getViewHolder(context, viewType)
    }

    protected open fun getViewHolder(context: Context, viewType: Int): RecyclerView.ViewHolder {
        return DefaultViewHolder(getCardView(context, viewType))
    }

    /**
     * Binding of each card
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= 0) {
            val itemView = holder.itemView
            val data = dataList[position]
            bindData(itemView, data, position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}