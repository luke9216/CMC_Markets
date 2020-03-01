package com.cmcmarkets.android.exercise.composer.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.ContentLoadingProgressBar
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
import java.util.*
import javax.inject.Inject


open class WatchlistListFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var watchlistViewModel: WatchlistViewModel

    lateinit var mRecyclerView: RecyclerView
    lateinit var loadingbar: ContentLoadingProgressBar

    private var mAdapter: ProductAdapter? = null

    private var currentSession: SessionTO? = null
    private var watchlists: ArrayList<WatchlistTO>? = null

    private var watchlistUpdatedProductList: ArrayList<ProductDTO>? = null
    private var watchlistProductIds: List<Long>? = null
    private var watchlistProducts: ArrayList<ProductTO>? = null
    private var watchlistProductsDetails: ArrayList<ProductDetailsTO>? = null
    private var watchlistProductsPrice: ArrayList<PriceTO>? = null

    override fun initEvent() {
        initObservers()

        val session = watchlistViewModel.onGetSession()
        if (session != null) {
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
        loadingbar = view.findViewById(R.id.loadingbar)
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
        if (watchlists != null) {
            PopupWatchlistSelect.initSelectUserPopup(it, watchlists)
            PopupWatchlistSelect.listener = { watchlist ->
                handleWatchlistSelect(watchlist)
            }
        }
    }

    /**
     * Handle the watchlist
     *
     * @param watchlist - User selected watchlist
     */
    private fun handleWatchlistSelect(watchlist: WatchlistTO) {
        activity?.findViewById<Button>(R.id.btnSelectWatchlist)?.text = watchlist.details.name
        val productIds = watchlist.details.productIds
        watchlistViewModel.clearProducts()                                      //Empty the previous products
        watchlistViewModel.setProductPriceSize(productIds.size)                 //Set new size of watchlist for the product prices array
        watchlistProductIds = productIds
        productIds.forEachIndexed { index, productId ->
            watchlistViewModel.onGetProduct(currentSession!!.token, productId)                          //Rx call
            watchlistViewModel.onGetProductDetails(currentSession!!.token, productId)
            watchlistViewModel.onGetProductPrice(currentSession!!.token, productId, productIds.size)
        }
        initPriceObserver()
    }

    /**
     * Init observers needed for the watchlists and products
     *
     */
    private fun initObservers() {
        watchlistViewModel.watchlist.observe(this, Observer {
            if (it != null) {
                watchlists = it as ArrayList
            }
        })

        watchlistViewModel.product.observe(this, Observer {
            if (it != null) {
                watchlistProducts = it
                updateWatchlistRecyclerView()
            }
        })
        watchlistViewModel.productDetails.observe(this, Observer {
            if (it != null) {
                watchlistProductsDetails = it
                updateWatchlistRecyclerView()
            }
        })
        watchlistViewModel.productLoadingStatus.observe(this, Observer {
            if (it != null) {
                if(it == WatchlistViewModel.LoadingStatus.LOADING){
                    loadingbar.show()
                } else {
                    loadingbar.hide()
                }
            }
        })
        watchlistViewModel.watchlistLoadingStatus.observe(this, Observer {
            if (it != null) {
                if(it == WatchlistViewModel.LoadingStatus.LOADING){
                    loadingbar.show()
                } else {
                    loadingbar.hide()
                }
            }
        })
    }

    /**
     * Init the price observer. This has to be called after handleWatchlistSelect as the watchlist size needs to be set.
     *
     * An observable is created for each product. So price is flowable.
     *
     */
    private fun initPriceObserver() {
        watchlistViewModel.productPriceArrayList.forEachIndexed { int, productPrice ->
            productPrice.observe(this, Observer {
                val updateProductList = watchlistUpdatedProductList
                if (it != null && updateProductList != null && int < updateProductList.size) {
                    updateProductList[int].priceTO = it
                    mAdapter?.updatePrice(updateProductList[int], int)
                }
            })
        }
    }

    /**
     * Method is aimed to run all the way through when all of the basic details of the products are received.
     * ProductDTO (ProductTO, ProductDetailsTO and PriceTO) array list created with all product details and passed to recyclerview.
     * nulls are expected for ProductsDetails and ProductsPrice. Aiming to get response time quick for user
     *
     */
    private fun updateWatchlistRecyclerView() {
        val watchlistProductIds = watchlistProductIds ?: return
        val watchlistProducts = watchlistProducts ?: return
        val updatedProduct: ArrayList<ProductDTO> = arrayListOf()

        val watchlistProductsDetails = watchlistProductsDetails
        val watchlistProductsPrice = watchlistProductsPrice

        watchlistProducts.forEachIndexed { index, product ->
            var productId: Long? = null
            if (index < watchlistProductIds.size - 1) {
                productId = watchlistProductIds[index]
            }
            var productDetails: ProductDetailsTO? = null
            if (watchlistProductsDetails != null && index < watchlistProductsDetails.size - 1) {
                productDetails = watchlistProductsDetails[index]
            }
            var price: PriceTO? = null
            if (watchlistProductsPrice != null && index < watchlistProductsPrice.size - 1) {
                price = watchlistProductsPrice[index]
            }
            updatedProduct.add(ProductDTO(
                    productId,
                    product,
                    productDetails,
                    price
            ))
        }

        watchlistUpdatedProductList = updatedProduct
        val adapter = ProductAdapter(updatedProduct)

        mRecyclerView.layoutManager = LinearLayoutManager(this.context)
        mRecyclerView.adapter = adapter
        mAdapter = adapter
    }
}
