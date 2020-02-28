package com.cmcmarkets.api.products


data class WatchlistTO(val id: String, val details: WatchlistDetailsTO)

data class WatchlistDetailsTO(val name: String, val productIds: List<Long>)
