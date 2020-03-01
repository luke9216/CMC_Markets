package com.cmcmarkets.android.data.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cmcmarkets.android.data.domain.repository.ProductRepository
import com.cmcmarkets.android.data.domain.repository.SessionRepository
import com.cmcmarkets.android.data.domain.repository.WatchlistRepository
import com.cmcmarkets.android.exercise.base.BaseViewModel
import com.cmcmarkets.api.products.*
import com.cmcmarkets.api.session.SessionTO
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Handler
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.concurrent.timerTask


class WatchlistViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var sessionRepository: SessionRepository

    @Inject
    lateinit var productRepository: ProductRepository

    @Inject
    lateinit var watchlistRepository: WatchlistRepository

    private val _session = MutableLiveData<SessionTO>()
    val session: LiveData<SessionTO> = _session

    private val _sessionLoadingStatus = MutableLiveData<LoadingStatus>()
    val sessionLoadingStatus: LiveData<LoadingStatus> = _sessionLoadingStatus

    enum class LoadingStatus { LOADING, NOT_LOADING }

    fun onCreateSession() = sessionRepository.getInstance()?.createSession()!!
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnSubscribe { _sessionLoadingStatus.postValue(LoadingStatus.LOADING) }
            .doFinally { _sessionLoadingStatus.postValue(LoadingStatus.NOT_LOADING) }
            .subscribeBy(
                    onSuccess = {
                        _session.postValue(it)
                        sessionRepository.updateSession(it)
                    },
                    onError = {
                        //TODO Log Error
                        val error = it.message
                        val reason = it.stackTrace
                        _session.postValue(null)
                    }
            )

    fun onGetSession(): SessionTO? = sessionRepository.getInstance()?.getSession()

    fun clearProducts() {
        _product.value = null
        _productDetails.value = null
        _productPriceArrayList.clear()
        productPriceArrayList.clear()
    }

    /*TODO Inefficient (needs attention) - Do an ArrayList of MutableLiveData not MutableLiveData ArrayList
                                           want to update single elements in array
     */
    private val _product = MutableLiveData<ArrayList<ProductTO>>()
    val product: LiveData<ArrayList<ProductTO>> = _product

    private val _productLoadingStatus = MutableLiveData<LoadingStatus>()
    val productLoadingStatus: LiveData<LoadingStatus> = _productLoadingStatus

    private val _productDetails = MutableLiveData<ArrayList<ProductDetailsTO>>()
    val productDetails: LiveData<ArrayList<ProductDetailsTO>> = _productDetails

    private val _productPrice = MutableLiveData<PriceTO>()
    private val _productPriceArrayList = ArrayList<MutableLiveData<PriceTO>>()
    val productPriceArrayList = ArrayList<LiveData<PriceTO>>()

    fun onGetProduct(sessionToken: String, id: Long) = productRepository.productSingle(sessionToken, id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .doOnSubscribe { _productLoadingStatus.postValue(LoadingStatus.LOADING) }
            .doFinally { _productLoadingStatus.postValue(LoadingStatus.NOT_LOADING) }
            .subscribeBy(
                    onSuccess = {
                        var productList: ArrayList<ProductTO> = arrayListOf()
                        if (_product.value != null) {
                            productList = _product.value as ArrayList
                        }
                        productList.add(it)
                        _product.postValue(productList)
                    },
                    onError = {
                        //TODO Log Error
                        val error = it.message
                        val reason = it.stackTrace
                        _product.postValue(null)
                    }
            )

    fun onGetProductDetails(sessionToken: String, id: Long) = productRepository.productDetailsSingle(sessionToken, id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeBy(
                    onSuccess = {
                        var productDetailsList: ArrayList<ProductDetailsTO> = arrayListOf()
                        if (_productDetails.value != null) {
                            productDetailsList = _productDetails.value as ArrayList
                        }
                        productDetailsList.add(it)
                        _productDetails.postValue(productDetailsList)
                    },
                    onError = {
                        //TODO Log Error
                        val error = it.message
                        val reason = it.stackTrace
                        _productDetails.postValue(null)
                    }
            )

    fun setProductPriceSize(size: Int) {
        var i = 0
        while (i <= size) {
            _productPriceArrayList.add(_productPrice)
            productPriceArrayList.add(_productPrice as LiveData<PriceTO>)
            i++
        }
    }

    fun onGetProductPrice(sessionToken: String, id: Long, index: Int) = productRepository.productPrices(sessionToken, id)
            .subscribeOn(Schedulers.newThread()).onBackpressureLatest()
            .observeOn(Schedulers.newThread()).onBackpressureLatest()
            .subscribeBy(
                    onNext = {
                        _productPriceArrayList[index].postValue(it)
                    },
                    onError = {
                        //TODO Log Error
                        val error = it.message
                        val reason = it.stackTrace
//                            _productPriceArrayList[index].postValue(null)
                    }
            )

    private val _watchlist = MutableLiveData<List<WatchlistTO>>()
    val watchlist: LiveData<List<WatchlistTO>> = _watchlist

    private val _watchlistLoadingStatus = MutableLiveData<LoadingStatus>()
    val watchlistLoadingStatus: LiveData<LoadingStatus> = _watchlistLoadingStatus

    fun onGetWatchlist(sessionToken: String) = watchlistRepository.watchlistsSingle(sessionToken)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnSubscribe { _watchlistLoadingStatus.postValue(LoadingStatus.LOADING) }
            .doFinally { _watchlistLoadingStatus.postValue(LoadingStatus.NOT_LOADING) }
            .subscribeBy(
                    onSuccess = { _watchlist.postValue(it) },
                    onError = {
                        //TODO Log Error
                        val error = it.message
                        val reason = it.stackTrace
                        _watchlist.postValue(null)
                    }
            )

    private val _watchlistUpdate = MutableLiveData<WatchlistUpdateTO>()
    val watchlistUpdate: LiveData<WatchlistUpdateTO> = _watchlistUpdate

    fun onGetWatchlistUpdate(sessionToken: String) = watchlistRepository.watchlistUpdatesObservable(sessionToken)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy(
                    onNext = { _watchlistUpdate.postValue(it) },
                    onError = {
                        //TODO Log Error
                        val error = it.message
                        val reason = it.stackTrace
                        _watchlistUpdate.postValue(null)
                    }
            )

}



