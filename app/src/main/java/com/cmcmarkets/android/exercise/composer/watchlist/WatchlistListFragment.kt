package com.cmcmarkets.android.exercise.composer.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmcmarkets.android.data.domain.viewmodel.WatchlistViewModel
import com.cmcmarkets.android.data.gateway.entity.ProductDTO
import com.cmcmarkets.android.exercise.R
import com.cmcmarkets.android.exercise.base.BaseFragment
import com.cmcmarkets.android.exercise.common.adapter.ProductAdapter
import com.cmcmarkets.api.products.PriceTO
import com.cmcmarkets.api.products.ProductDetailsTO
import com.cmcmarkets.api.products.ProductTO
import com.cmcmarkets.api.products.WatchlistTO
import com.cmcmarkets.api.session.SessionTO
import javax.inject.Inject


open class WatchlistListFragment @Inject constructor(): BaseFragment() {

    @Inject
    lateinit var watchlistViewModel: WatchlistViewModel

    lateinit var mRecyclerView: RecyclerView

    private var currentSession: SessionTO? = null
    private var watchlists: ArrayList<WatchlistTO>? = null

    private var watchlistProductIds: List<Long>? = null
    private var watchlistProducts: ArrayList<ProductTO>? = null
    private var watchlistProductsDetails: ArrayList<ProductDetailsTO>? = null
    private var watchlistProductsPrice: ArrayList<PriceTO>? = null


    override fun initEvent() {
        initObservers()

        val session = watchlistViewModel.onGetSession()
        if(session != null) {
            watchlistViewModel.onGetWatchlist(session.token)
            currentSession = session
        } else {
            //TODO Error
        }
    }

    override fun initView() {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_watchlists, container, false)
        mRecyclerView = view.findViewById(R.id.products)
        popupDialogWatchlists(view)
        return view
    }

    private fun popupDialogWatchlists(view: View) {
        val btnSelectUser = view.findViewById(R.id.btnSelectWatchlist) as Button
        btnSelectUser.setOnClickListener {
            onSelectWatchlistBtn(it)
        }
    }

    private fun onSelectWatchlistBtn(it: View) {
        val watchlists = watchlists
        if(watchlists != null) {
            PopupWatchlistSelect.initSelectUserPopup(it, watchlists)
            PopupWatchlistSelect.listener = { watchlist ->
                handleWatchlistSelect(watchlist)
            }
        }
    }

    private fun handleWatchlistSelect(watchlist: WatchlistTO) {
        activity?.findViewById<Button>(R.id.btnSelectWatchlist)?.text = watchlist.details.name
        watchlistViewModel.clearProducts()
        val productIds = watchlist.details.productIds
        watchlistProductIds = productIds
        productIds.forEach { productId ->
            watchlistViewModel.onGetProduct(currentSession!!.token, productId)
            watchlistViewModel.onGetProductDetails(currentSession!!.token, productId)
            watchlistViewModel.onGetProductPrice(currentSession!!.token, productId)
        }
    }

    private fun initObservers() {
        watchlistViewModel.watchlist.observe(this, Observer {
            if(it != null) {
                watchlists = it as ArrayList
            }
        })

        watchlistViewModel.product.observe(this, Observer {
            if(it != null) {
                watchlistProducts = it
                updateWatchlistRecyclerView()
            }
        })
        watchlistViewModel.productDetails.observe(this, Observer {
            if(it != null) {
                watchlistProductsDetails = it
                updateWatchlistRecyclerView()
            }
        })
        watchlistViewModel.productPrice.observe(this, Observer {
            if(it != null) {
//                watchlistProductsPrice = it
            }
        })
    }

    private fun updateWatchlistRecyclerView() {

        val updatedProduct: ArrayList<ProductDTO> = arrayListOf()

        val watchlistProductIds = watchlistProductIds ?: return
        val watchlistProducts = watchlistProducts
        val watchlistProductsDetails = watchlistProductsDetails

        val iProductIds = watchlistProductIds.iterator()

        val index = 0
        while (iProductIds.hasNext()){
            val product = watchlistProducts?.get(index)
            val productDetails = watchlistProductsDetails?.get(index)

            updatedProduct.add(ProductDTO(
                    iProductIds.next(),
                    product,
                    productDetails
            ))
        }

        val adapter = ProductAdapter(updatedProduct)

        mRecyclerView.layoutManager = LinearLayoutManager(this.context)
        mRecyclerView.adapter = adapter
    }
}
