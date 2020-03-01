package com.cmcmarkets.android.exercise.common.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cmcmarkets.android.data.gateway.entity.ProductDTO
import com.cmcmarkets.android.exercise.R
import com.cmcmarkets.android.exercise.base.BaseRecyclerViewAdapter
import com.cmcmarkets.android.exercise.common.card.ProductCard
import com.cmcmarkets.android.exercise.common.card.WatchlistsCard
import com.cmcmarkets.api.products.ProductTO
import com.cmcmarkets.api.products.WatchlistTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductAdapter @Inject constructor(productList: ArrayList<ProductDTO>) :
        BaseRecyclerViewAdapter<ProductDTO>(productList) {

    private var mItemView: View? = null

    var onProductClicked: ((ProductDTO) -> Unit)? = null

    override fun getCardView(context: Context, viewType: Int): View {
        val view = ProductCard(context)
        mItemView = view
        return view
    }

    override fun bindData(itemView: View, data: ProductDTO, position: Int) {
        if(itemView is ProductCard) {
            itemView.setProduct(data)
        }
    }

    /**
     * Update the price of the product. Code needs to be efficient as it is called frequently.
     *
     * @param data - Product, ProductDetails and Price as ArrayList
     * @param position - Position to change in recyclerview
     */
    fun updatePrice(data: ProductDTO, position: Int) {
        if(mItemView != null) {
            bindData(mItemView!!, data, position)
            notifyItemChanged(position)
        }
    }

    override fun getItemClickListener(): ((ProductDTO) -> Unit)? {
        return onProductClicked
    }
}