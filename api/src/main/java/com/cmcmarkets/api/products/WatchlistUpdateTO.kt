package com.cmcmarkets.api.products

data class WatchlistUpdateTO(
        val id: String,
        val operation: WatchlistOperationType,
        val updatedDetails: WatchlistDetailsTO?
)

enum class WatchlistOperationType {
    ADD_OR_UPDATE,
    DELETE
}
