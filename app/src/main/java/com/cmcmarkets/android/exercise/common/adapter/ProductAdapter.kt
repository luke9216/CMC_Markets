package com.cmcmarkets.android.exercise.common.adapter

import android.content.Context
import android.view.View
import com.cmcmarkets.android.data.gateway.entity.ProductDTO
import com.cmcmarkets.android.exercise.base.BaseRecyclerViewAdapter
import com.cmcmarkets.android.exercise.common.card.ProductCard
import com.cmcmarkets.api.products.ProductTO
import javax.inject.Inject

class ProductAdapter @Inject constructor(productList: ArrayList<ProductDTO>) :
        BaseRecyclerViewAdapter<ProductDTO>(productList) {

    var onProductClicked: ((ProductDTO) -> Unit)? = null

    override fun getCardView(context: Context, viewType: Int): View {
        return ProductCard(context)
    }

    override fun bindData(itemView: View, data: ProductDTO, position: Int) {
        if(itemView is ProductCard) {
            itemView.setProduct(data)
        }
    }

    override fun getItemClickListener(): ((ProductDTO) -> Unit)? {
        return onProductClicked
    }
}